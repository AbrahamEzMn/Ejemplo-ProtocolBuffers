# http_server.rb
require 'socket'
require './proto/person_pb'
require './proto/phoneNumber_pb'
require './proto/phoneType_pb'

server = TCPServer.new 5678

while session = server.accept
  request = session.gets
  puts request

  person = Messages::Person.new(:name => "Abraham", :id => 2)
  person.email = "abraham.esparza.m010@gmail.com"

  session.print "HTTP/1.1 200\r\n" # 1
  session.print "Content-Type: text/html\r\n" # 2
  session.print "\r\n" # 3
  session.print "Hello world! The time is #{Time.now}" #4

  puts Messages::Person.encode_json(person)

  session.close
end