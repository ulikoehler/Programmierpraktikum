#!/usr/bin/env ruby
require("rest_client")
response = RestClient.get 'http://pdb.org'
File.open(local_filename, 'w') {|f| f.write(response.to_str()) }