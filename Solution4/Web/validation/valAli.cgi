#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);

#my $cgi = new cgi;

my $alignments = param("alignments");
my $reference = param("reference");
my $text = param ("text");

print $cgi->header();
print "<!Doctype><html>test22</html>";
