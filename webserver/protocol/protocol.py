__author__ = 'troels'
import re

class Protocol:

    def __init__(self):
        pass

    @staticmethod
    def get_request_type(self, request):
        header = request.recv(100)
        match = re.match('(\w+){3,4}\s/([\w+/+])?\sHTTP/1', header)
        if match.