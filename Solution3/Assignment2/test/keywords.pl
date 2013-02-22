#!/usr/bin/perl -w
#UNFINISHED
use DBI;
#Fetch or insert a keyword
sub getKeywordId {
	my $db = $_[0];
	my $keyword = $_[1];
	#Try to find existing keywordx
	my $findKeywordIdStmt = $db->prepare("Select `Id` FROM Keywords WHERE `Description` = ?");
	$findKeywordIdStmt->execute($keyword);
	my $result = $findKeywordIdStmt->fetchrow_hashref();
	if(defined $result->{Id}) {
		return $result->{Id};
	}
	#If that didn't return, the keyword doesn't exist. insert it.
	my $insertStmt = $db->prepare("INSERT INTO Keywords(`Description`) VALUES (?)");
        $insertStmt->execute($keyword);
        $autoIncrementId = $insertStmt->{mysql_insertid};
        #Fetch it again (there may be a better way)
        return $autoIncrementId;
}

sub insertKeywordRelation {
	my $db = $_[0];
	my $keyword = $_[1];
	my $seqDBId = $_[2];
	#Try to fetch an existing organism
	my $keywordId = getKeywordId($db, $keyword);
	my $insertStmt = $db->prepare("INSERT INTO KeySeq(`KeywordsId`,`SeqId`) VALUES (?,?)");
        $insertStmt->execute($keywordId, $seqDBId);
        $autoIncrementId = $insertStmt->{mysql_insertid};
        #Return that
        return $autoIncrementId;
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