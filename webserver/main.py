from webserver.server.HttpServer import HttpServer

server = HttpServer('127.0.0.1', 8080)
server.start_server()