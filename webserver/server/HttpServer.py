import socket
import re
from webserver.protocol.HttpGet import HttpGet


class HttpServer:

    host = None
    port = None
    sock = None

    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.bind(
            (self.host, self.port)
        )
        self.sock.listen(1)

    def start_server(self):
        while True:
            csock, caddr = self.sock.accept()  # csock is the request object, caddr is the address/port pair.
            self.handle_request(csock)

    def handle_request(self, csock):
        request = csock.recv(8192)  # returns request headers. (no more than 8kb)
        match = re.match("(\w+)\s/([\w\d/]+(\.\w+)?)?\sHTTP/1", request)

        if match is None:  # something wierd happened?
            return

        request_type = match.group(1).upper()

        if request_type == "GET":
            HttpGet(match, csock)
        elif request_type == "POST":
            return
        elif request_type == "HEAD":
            return
