#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
print header("application/json");
use DBI;
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $findAllMatrices = $db->prepare("SELECT Name FROM Matrices;");
#Write header
print "[";
my $isFirstLine = 1;
$findAllMatrices->execute();
while (my $row = $findAllMatrices->fetchrow_hashref()) {
	print "," unless $isFirstLine;
	$isFirstLine = 0;
	print "\"" . $row->{Name} . "\"";
}
print "]";
$db->disconnect();
