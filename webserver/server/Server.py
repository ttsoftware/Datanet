import socket
from webserver.protocol.Protocol import Protocol


class Server

host = '127.0.0.1'
port = 8083
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind((host, port))
sock.listen(1)

start_server(sock)


def start_server(socket):

    while True:
        csock, caddr = sock.accept()  # csock is the request object, caddr is the address/port pair.

        Protocol.handle_request(csock)

        csock.close()