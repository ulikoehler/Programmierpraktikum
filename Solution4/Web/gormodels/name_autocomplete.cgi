#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
print header("application/json");
use DBI;
#print header("text/plain");
#
my $limit = param("limit") || 100;
my $prefix = param("prefix") || "";
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $query = $db->prepare("SELECT GORModels.Name FROM GORModels WHERE LCASE(GORModels.Name) LIKE LCASE(?) LIMIT ?;");
#Write header
print "[";
my $isFirstLine = 1;
$query->execute($prefix."%",$limit);
while (my $row = $query->fetchrow_hashref()) {
	print "," unless $isFirstLine;
	$isFirstLine = 0;
	print "\"" . $row->{Name} . "\"";
}
print "]";
$db->disconnect();
