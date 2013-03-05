#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);

my $seq1ID = param("seq1Id");
my $seq2ID = param("seq2Id");

my distanceMatix = param("distanceMatrix");
my $gapOpen = param("gapOpenPenalty");
my $gapExtend = param("gapExtendPenalty");

my $alignmentType = param("alignmentType");
my $alignmentAlgo = param("alignmentAlgorithm");

my $calcSSAA = param("calculateSSAA");


my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";

#getMatrixFromDatabase($db, $matrixName, $outputFile) get QUASAR matrix from
sub getMatrixFromDatabase {
	my $db = $_[0];
	my $matrixName = $_[1];
	my $outputFile = $_[2];

	my $query = $db->prepare("SELECT QUASAR FROM Matrices WHERE LCASE(Matrices.Name) = LCASE(?)");
	$query->execute($matrixName);
	my $row = $query->fetchrow_hashref()
	if(not defined $row) {
		die "Matrix with name $matrixName can't be found in database";
	}
	my $quasar = $row->{QUASAR}
	#Write it to a files
	open (OUTFILE, ">$outputFile");
	print OUTFILE, $quasar);
	close(OUTFILE);
}


