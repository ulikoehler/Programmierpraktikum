#!/usr/bin/perl -w
use strict;
use LWP::Simple;
use CGI qw(:standard);
#Parse the CGI arg
my $pdbId = param("pdbid");
die "Please use PDB ID GET parameter!\n" unless $pdbId;
#Execute the shell script from assignment 10 if the file isnt cached
unless(-e "pdbcache/$pdbId.pdb") {
	`bash call_rasmol.sh $pdbId`;
}
#Move the file to the cache directory
use File::Copy;
copy($pdbId.".pdb", "pdbcache/$pdbId.pdb");
unlink $pdbId.".pdb";
#Print the data (only executed if not error occured before
print redirect(url_param("pdbcache/$pdbId.pdb"));