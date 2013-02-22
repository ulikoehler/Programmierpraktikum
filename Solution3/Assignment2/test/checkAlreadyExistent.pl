#!/usr/bin/perl -w
use DBI;
#Get the organism ID, insert it if not existent
sub checkIfSwissprotEntryExists {
	my $db = $_[0];
	my $primaryAC = $_[1];
	#Try to fetch an existing organism
	my $insertSeqDBEntryStmt = $db->prepare("Select `Id` from SeqDBEntry WHERE `SeqDBIdentifier` = ? AND `SeqDBId` =  (SELECT `Id` FROM SeqDB WHERE `Name` = 'SwissProt');");
	$insertSeqDBEntryStmt->execute($primaryAC);
	my $result = $insertSeqDBEntryStmt->fetchrow_hashref();
	if(defined $result->{Id}) {
		return 1;
	} else {
		return 0;
	}
}

$db = DBI->connect('DBI:mysql:bioprakt4;host=localgrid.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
$id = checkIfSwissprotEntryExists($db, "P10275");
if($id) {
	print "x";
}
print "Got result $id\n";
$id = checkIfSwissprotEntryExists($db, "P00000001");
if($id) {
	print "y";
}
print "Got ID $id\n";