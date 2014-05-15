import unittest
import os
import re


class TestProtocol(unittest.TestCase):
    def test_get_request_type(self):

        test_header1 = "GET /test/test.html HTTP/1.1\n\nHost: 127.0.0.1:8080\nConnection: keep-alive\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8,da;q=0.6,it;q=0.4,nb;q=0.2,sv;q=0.2"
        test_header2 = "GET /test.html HTTP/1.1\n\nHost: 127.0.0.1:8080\nConnection: keep-alive\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8,da;q=0.6,it;q=0.4,nb;q=0.2,sv;q=0.2"
        test_header3 = "GET /test/test/test.html HTTP/1.1\n\nHost: 127.0.0.1:8080\nConnection: keep-alive\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8,da;q=0.6,it;q=0.4,nb;q=0.2,sv;q=0.2"
        test_header4 = "GET / HTTP/1.1\n\nHost: 127.0.0.1:8080\nConnection: keep-alive\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8,da;q=0.6,it;q=0.4,nb;q=0.2,sv;q=0.2"
        test_header5 = "GET /test HTTP/1.1\n\nHost: 127.0.0.1:8080\nConnection: keep-alive\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8,da;q=0.6,it;q=0.4,nb;q=0.2,sv;q=0.2"

        reg = '(\w+)\s/([^\.]+)?([\w\d]+\.html?)?\sHTTP/1'

        match1 = re.match(reg, test_header1)
        match2 = re.match(reg, test_header2)
        match3 = re.match(reg, test_header3)
        match4 = re.match(reg, test_header4)
        match5 = re.match(reg, test_header5)

        self.assertEqual(match1.group(1), "GET")
        self.assertEqual(match1.group(2), "/test")
        self.assertEqual(match1.group(3), "/test.html")

        self.assertEqual(match2.group(1), "GET")
        self.assertEqual(match2.group(2), "")
        self.assertEqual(match2.group(3), "/test.html")

        self.assertEqual(match3.group(1), "GET")
        self.assertEqual(match3.group(2), "/test/test")
        self.assertEqual(match3.group(3), "/test.html")

        self.assertEqual(match4.group(1), "GET")
        self.assertEqual(match4.group(2), None)
        self.assertEqual(match4.group(3), None)

        self.assertEqual(match5.group(1), "GET")
        self.assertEqual(match5.group(2), "/test")
        self.assertEqual(match5.group(3), None)

    def test_list_dir(self):
        print os.listdir("/var/www/")

if __name__ == '__main__':
    unittest.main()
