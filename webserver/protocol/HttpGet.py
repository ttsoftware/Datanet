import os
from webserver.Options import Options
from webserver.filesystem.Filesystem import Filesystem
from webserver.protocol.Communication import Communication
from webserver.protocol.HttpRequest import HttpRequest


class HttpGet(HttpRequest):

    def __init__(self, match, csock):

        filepath = match.group(2)

        print filepath

        filename = match.group(3)

        print filename

        if filename is None or filepath is None:  # directory index requested
            filename = "index.html"
            filepath = "/"
            if Filesystem.file_exists(filepath, filename):  # we look for index.html
                self.serve_file(filepath, filename, csock)
            else:  # no index file, so we serve directory listing
                files = os.listdir(Options.root_dir)
                Communication.reply_dir(csock, files)
        else:  # a file was specified, so we serve it.
            self.serve_file(filepath, filename, csock)
