

import SocketServer
import SimpleHTTPServer
import re
from messages_pb.person_pb2 import *
from messages_pb.phoneNumber_pb2 import *
from messages_pb.phoneType_pb2 import *

PORT = 9090

class CustomHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):

    def do_POST(self):
        if None != re.search('/message', self.path):

            content_len = int(self.headers.getheader('content-length', 0))
            post_body = self.rfile.read(content_len)

            person = Person()
            person.ParseFromString(post_body)
            #This URL will trigger our sample function and send what it returns back to the browser
            self.send_response(200)
            self.send_header('Content-type','text/html')
            self.end_headers()

            self.wfile.write(person.name) 
            return


    def do_GET(self):
        if None != re.search('/message', self.path):
            
            #This URL will trigger our sample function and send what it returns back to the browser
            self.send_response(200)
            self.send_header('Content-type','application/x-protobuf')
            self.end_headers()

            person = Person()
            person.name         = 'Abraham Esparza Moreno'
            person.email        = 'abraham.esparza.m010@gmail.com'
            person.id           =  1
            phoneNumber         = person.phone.add()
            phoneNumber.number  = '449123456789'
            phoneNumber.type    = WORK
            phoneNumber         = person.phone.add()
            phoneNumber.number  = '4499500963'
            phoneNumber.type    = HOME

            self.wfile.write(person.SerializeToString()) #call sample function here
            return
        else:
            #serve files, and directory listings by following self.path from
            #current working directory
            SimpleHTTPServer.SimpleHTTPRequestHandler.do_GET(self)

httpd = SocketServer.ThreadingTCPServer(('', PORT),CustomHandler)

print "serving at port", PORT
httpd.serve_forever()