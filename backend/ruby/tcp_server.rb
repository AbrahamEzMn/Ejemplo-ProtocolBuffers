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

  person.phone.push(
    Messages::PhoneNumber.new(:number => '3216549874', :type => Messages::PhoneType::HOME)
  )

  session.print "HTTP/1.1 200\r\n" # 1
  session.print "Content-Type: application/x-protobuf\r\n" # 2
  session.print Messages::Person.encode(person)

  puts Messages::Person.encode_json(person)

  session.close
end