import unittest
import os
import re


class TestProtocol(unittest.TestCase):
    def test_get_request_type(self):

        test_header = "GET /test.html HTTP/1.1\n\nHost: 127.0.0.1:8080\nConnection: keep-alive\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8,da;q=0.6,it;q=0.4,nb;q=0.2,sv;q=0.2"

        match = re.match('(\w+)\s/([\w\d/]+(\.\w+)?)?\sHTTP/1', test_header)

        self.assertEqual(match.group(1), "GET")
        self.assertEqual(match.group(2), "test.html")

    def test_list_dir(self):
        print os.listdir("/var/www/")

if __name__ == '__main__':
    unittest.main()
