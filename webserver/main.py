from webserver.server.HttpServer import HttpServer

server = HttpServer('127.0.0.1', 8081)
server.start_server()