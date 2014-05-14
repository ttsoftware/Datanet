import os
from webserver.Options import Options


class Filesystem:

    @staticmethod
    def file_exists(filename):
        files = os.listdir(Options.root_dir)
        for sysfile in files:
            if sysfile == filename:
                return True
        return False