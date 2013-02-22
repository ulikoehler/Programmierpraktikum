#!/usr/bin/env perl -w
use DBI;
#Get the organism ID, insert it if not existent
sub getOrganismId {
	my $db = $_[0];
	my $organismName = $_[1];
	#Try to fetch an existing organism
	$db->do("SELECT Id from Organism WHERE Name = ?;", undef, $organism);
	my $result = $sth->fetchrow_hashref();
	if(defined $result->{Id}) {
		return $result->{Id}
	}
	#If that didn't return, we need to insert it
	$db->do("INSERT INTO Organism('Name') VALUES (?);", undef, $organism);
	#Fetch it again (there may be a better way)
	$db->do("SELECT Id from Organism WHERE Name = ?;", undef, $organism);
	my $result = $sth->fetchrow_hashref();
	if(defined $result->{Id}) {
		return $result->{Id}
	}
}

$db = DBI->connect('DBI:mysql:bioprakt4;host=localgrid.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
$id = getOrganismId($db, "Escherichia Coli sp. Strain 4");
print "Got ID $id\n";