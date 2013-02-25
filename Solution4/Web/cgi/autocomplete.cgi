#!/usr/bin/perl -w
use strict;
use CGI::Carp qw(fatalsToBrowser);
use CGI qw(:standard);
print header("application/json");
die "adb";
#Parse params
my $limit = param("limit") || 100;
my $db = "pdb";
#Write header
print "[";
if ($db eq "pdb") {
	open PDBIDS, "<pdbids" or die $!;
	my $isFirstLine = 1;
	my $entriesLeft  = $limit;
	while (<PDBIDS>) {
		chomp $_;
		print "," unless $isFirstLine;
		$isFirstLine = 0;
		$entriesLeft--;
		print "\"$_\"";
		#Break if limit reached
		last if $entriesLeft <= 0;
	}
	close PDBIDS;
}
print "]";