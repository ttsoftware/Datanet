import os
from webserver.Options import Options


class Filesystem:

    @staticmethod
    def file_exists(filepath, filename):

        filename = filename.replace("/", "")
        files = os.listdir(Options.root_dir + filepath)

        for sysfile in files:
            if sysfile == filename:
                return True
        return False