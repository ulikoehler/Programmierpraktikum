#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use DBI;
#print header("application/json");
my $name = param("name");
my $datafile = param("quasar");
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $insertStmt = $db->prepare("INSERT INTO Matrices (`Name`, `QUASAR`) VALUES(?,?)");
#Read the data
binmode $datafile;
my $data = "";
while(read $datafile,$inputdata,1024) {
  $data = $data.$inputdata;
}
#Write it to the DB
$insertStmt->execute($name, $data);
#Write header
#print "{\"success\":true,\"name\":$name}";
print redirect(referer());
$db->disconnect();
