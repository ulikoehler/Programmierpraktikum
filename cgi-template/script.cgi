#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
print header;
#
print "<html><body>This is a ",param('param'),"</body></html>"