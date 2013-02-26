#!/usr/bin/perl -w
#Import stuff into autocomplete database
use strict;
use DBI;
use Getopt::Std;
#Subs
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $insert = $db->prepare("INSERT DELAYED IGNORE INTO IDAutocomplete (`DBId`,`Name`) SELECT Id, ? FROM DB WHERE DB.Name = ?");
#PDBID
open PDBIDS, "<data/pdbids" or die $!;
while (<PDBIDS>) {
	#Print i
	chomp $_;
	$insert->execute($_, "PDB");
	print "Inserted $_\n";
}
close PDBIDS;
#Swissprot
open PDBIDS, "<data/swissprotids" or die $!;
while (<PDBIDS>) {
	#Print i
	chomp $_;
	$insert->execute($_, "SwissProt");
	print "Inserted $_\n";
}
close PDBIDS;
$db->disconnect();
