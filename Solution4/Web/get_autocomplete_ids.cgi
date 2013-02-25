#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
print header;
#print header("text/plain");
#
my $limit = param("limit") || 100;
my $prefix = param("prefix") || "";
my $db = "pdb";
#Write header
print "[";
if ($db eq "pdb") {
	open PDBIDS, "<pdbids" or die $!;
	my $isFirstLine = 1;
	my $entriesLeft  = $limit;
	while (<PDBIDS>) {
		#Check if it matches the prefix
		next unless $_ =~ m/^$prefix/;
		#Print i
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