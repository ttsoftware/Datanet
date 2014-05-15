from webserver.Options import Options


class Response:

    @staticmethod
    def reply_dir(csock, files):

        with open(Options.root_dir + "/standard/list_dir.html", 'r') as fhandle:
            content = fhandle.read()

        content = content.replace("%elements%", '<br/>'.join(files))

        csock.sendall("""HTTP/1.1 200 OK
                         Content-Type: text/html
                         Content-Length: """ + str(len(content)) + """
                         \n\n""" + content + """\r\n""")

    @staticmethod
    def reply_200(csock, content):
        csock.sendall("""HTTP/1.1 200 OK
                         Content-Type: text/html
                         Content-Length: """ + str(len(content)) + """
                         \n\n""" + content + """\r\n""")

    @staticmethod
    def reply_404(csock):
        with open(Options.root_dir + "/standard/404.html", 'r') as fhandle:
            content = fhandle.read()
        csock.sendall("""HTTP/1.1 404 Not Found
                         Content-Type: text/html
                         Content-Length: """ + str(len(content)) + """
                         \n\n""" + content + """\r\n""")  # file does not exist on server