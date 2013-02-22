#!/usr/bin/env perl -w
use DBI;
#Get the organism ID, insert it if not existent
sub getOrganismId {
	my $db = $_[0];
	my $organism = $_[1];
	#Try to fetch an existing organism
	my $queryOrganismIdStmt = $db->prepare("SELECT Id from Organism WHERE Name = ?;");
	$queryOrganismIdStmt->execute($organism);
	my $result = $queryOrganismIdStmt->fetchrow_hashref();
	if(defined $result->{Id}) {
		return $result->{Id}
	}
	#If that didn't return, we need to insert it
	my $insertStmt = $db->prepare("INSERT INTO Organism(`Name`) VALUES (?)");
	$insertStmt->execute($organism);
	$autoIncrementId = $insertStmt->{mysql_insertid};
	#Fetch it again (there may be a better way)
	return $autoIncrementId;
}

$db = DBI->connect('DBI:mysql:bioprakt4;host=localgrid.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
$id = getOrganismId($db, "Escherichia Coli sp. Strain 4");
print "Got ID $id\n";
$id = getOrganismId($db, "Escherichia Coli sp. Strain 5");
print "Got ID $id\n";