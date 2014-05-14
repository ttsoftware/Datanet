from webserver.Options import Options
from webserver.filesystem.Filesystem import Filesystem
from webserver.protocol.Communication import Protocol


class HttpCommand:

    def serve_file(self, filename, csock):
        exists = Filesystem.file_exists(filename)
        if exists:
            with open(Options.root_dir + filename, 'r') as fhandle:
                Protocol.reply_200(csock, fhandle.read())
        else:
            Protocol.reply_404(csock)