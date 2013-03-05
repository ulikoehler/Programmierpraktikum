#!/usr/bin/perl -w
#Insert a sequence by using a POST multipart/form-encoded file (not a string)
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use DBI;
#print header("application/json");
my $datafile = param("seqfile");
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $insertStmt = $db->prepare("INSERT INTO Seq (`Name`, `Seq`, `Type`, `OrganismId`) SELECT ?,?,?,Id from Organism WHERE Name = 'Unknown'");
#Create the name of the sequence (unique)
my $name = "user-" + time();
#Read the data
my $processedData = "";
binmode $datafile;
my $data = "";
while(read $datafile,$inputdata,1024) {
  $data = $data.$inputdata;
}
#Remove
for (split /^/, $data) {
	$processedData = $processedData.$_ if $_ !~ m/^>/;
}
#Write it to the DB
$insertStmt->execute($name, $processedData);
carp "Inserted user sequence $name into database\n"
#Write header
#print "{\"success\":true,\"name\":$name}";
#Write header
print header();
print <<"EOHTML"
<html>
<head>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/jstorage.js"></script>
    <script type="text/javascript" src="../ws.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.js"></script>
    <script type="text/javascript">
	addSequence(\"mysql:$name\", \"$name\", \"$originalSeqType\");
	window.history.back(-1);
    </script>
</head>
<body>
<h2>
</body>
</html>
EOHTML
;
$db->disconnect();
