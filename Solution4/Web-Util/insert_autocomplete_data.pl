#!/usr/bin/perl -w
#Import stuff into autocomplete database
use strict;
use Getopt::Std;
#Subs
$db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
$insert = $db->prepare("INSERT DELAYED INTO IDAutocomplete (`DBId`,`Name`) SELECT Id, ? FROM DB WHERE DB.Name = ?");
#PDBID
open PDBIDS, "<data/pdbids" or die $!;
while (<PDBIDS>) {
	#Print i
	chomp $_;
	$insert->execute($_, "PDB");
}
close PDBIDS;
#Swissprot
open PDBIDS, "<data/swissprotids" or die $!;
while (<PDBIDS>) {
	#Print i
	chomp $_;
	$insert->execute($_, "SwissProt");
}
close PDBIDS;
$db->disconnect();
