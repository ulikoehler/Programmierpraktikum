#!/usr/bin/perl -w
use strict;
use LWP::Simple;
use CGI qw(:standard);
#Parse the CGI arg
my $pdbId = param("pdbid");
#Download it
my $url = "http://www.pdb.org/pdb/download/downloadFile.do?fileFormat=pdb&compression=NO&structureId=" . $pdbId;
my $pdbData = get $url;
#Handle errors
print header(-status=>404) unless $pdbId;
print"Can't fetch PDB data for PDB ID $pdbId\n" unless $pdbData;
exit unless $pdbData;
#Print the data (only executed if not error occured before
print header('text/plain');
print $pdbData;
