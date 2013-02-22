#!/usr/bin/perl -w
use strict;
use LWP::Simple;
use CGI qw(:standard);
#Parse the CGI arg
my $pdbId = param("pdbid");
die "Please use PDB ID GET parameter!\n" unless $pdbId;
#Execute the shell script from assignment 10 if the file isnt cached
my $rootDir = "/home/k/koehleru/public_html/bioprakt/cgi";
#unless(-e rootDir."/pdbcache/$pdbId.png") {
#my $output = `/home/k/koehleru/public_html/bioprakt/cgi/call_rasmol.sh $pdbId`;
#print STDERR $output;
#}
#Move the file to the cache directory
use File::Copy;
copy($pdbId.".png", "./pdbcache/$pdbId.png");
#Print the data (only executed if not error occured before
print redirect("pdbcache/$pdbId.png");