#!/usr/bin/ruby
require 'cgi'
cgi = CGI.new
#How to parse GET (URL) or POST(form) parameters
#cgi['SomeGetParameter'] # =>  ["ValueOfTheParameter"]
#List of all GET/POST parameter keys: cgi.keys
#Write the response
puts cgi.header
puts "<html><body>This is a #{cgi["param"]}</body></html>"