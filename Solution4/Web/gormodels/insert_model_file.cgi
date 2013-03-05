#!/usr/bin/perl -w
#Insert a sequence by using a POST multipart/form-encoded file (not a string)
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use DBI;


sub trim {		# trim a string
  my $string = $_[0];
  $string =~ s/^\s+//;
  $string =~ s/\s+$//;
  return $string;
}
#print header("application/json");
my $datafile = param("gormodelfile");
my $name = param("name");
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $insertStmt = $db->prepare("INSERT INTO GORModels (`Name`, `Data`) VALUES(?,?)");
#Read the data
my $processedData = "";
binmode $datafile;
my $data = "";
while(read $datafile,$inputdata,1024) {
  $data = $data.$inputdata;
}
#Write it to the DB
$insertStmt->execute($name, $data);
carp "Inserted user GOR model $name into database, ID  $insertStmt->{mysql_insertid}\n";
#Write header
#print "{\"success\":true,\"name\":$name}";
#Write header
print header();
print <<"EOHTML"
<html>
<head>
    <script type="text/javascript">
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
