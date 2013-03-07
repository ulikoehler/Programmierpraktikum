#!/usr/bin/perl -w
#Insert a sequence by using a POST multipart/form-encoded file (not a string)
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use DBI;
#Write header
print header();

sub trim {		# trim a string
  my $string = $_[0];
  $string =~ s/^\s+//;
  $string =~ s/\s+$//;
  return $string;
}
#print header("application/json");
my $seqData = param("seq");
my $seqType = param("sequenceType");
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $insertStmt = $db->prepare("INSERT INTO Seq (`Name`, `Seq`, `Type`, `OrganismId`) SELECT ?,?,?,Id from Organism WHERE Organism.Name = 'Unknown'");
#Even if the real type is RNA, we currently use DNA
my $originalSeqType = $seqType;
$seqType = "DNA" if $seqType eq "Nucleotide";
#Create the name of the sequence (unique)
my $dbId = "user-" . time();
#Read the data
my $processedData = "";
#Remove
my $fastaHeader = "";
for (split /^/, $seqData) {
	chomp $_;
	if($_ !~ m/^>/) {
	    $processedData = $processedData.trim($_);
	} else {
	    $fastaHeader = substr($_, 1, length($_)-1);
	}
	chomp $processedData;
}
if(length($fastaHeader) > 40) {
  $fastaHeader = substr($fastaHeader, 0, 40) . "...";
}
my $sequenceName = trim("[FASTA Input] $fastaHeader");
#Write it to the DB
$insertStmt->execute($dbId, $processedData, $seqType);
my $id = $insertStmt->{mysql_insertid};
carp "Inserted user sequence $dbId into database, ID $id\n";
print <<"EOHTML"
<html>
<head>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/jstorage.js"></script>
    <script type="text/javascript" src="../ws.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.js"></script>
    <script type="text/javascript">
	addSequence(\"mysql:$dbId\", '$sequenceName', \"$originalSeqType\");
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
