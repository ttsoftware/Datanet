__author__ = 'troels'

import socket
import re

host = '127.0.0.1'
port = 8080
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind((host, port))
sock.listen(1)

# Loop forever, listening for requests:
while True:
    csock, caddr = sock.accept()
    print "Connection from: " + `caddr`
    req = csock.recv(1024)  # get the request, 1kB max
    print req
    # Look in the first line of the request for a move command
    # A move command should be e.g. 'http://server/move?a=90'
    match = re.match('GET /move\?a=(\d+)\sHTTP/1', req)
    if match:
        angle = match.group(1)
        print "ANGLE: " + angle + "\n"
        csock.sendall("""HTTP/1.0 200 OK
Content-Type: text/html

<html>
<head>
<title>Success</title>
</head>
<body>
Boo!
</body>
</html>
""")
    else:
        # If there was no recognised command then return a 404 (page not found)
        print "Returning 404"
        csock.sendall("HTTP/1.0 404 Not Found\r\n")
    csock.close()