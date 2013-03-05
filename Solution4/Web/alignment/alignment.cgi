#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use File::Temp qw/ tempfile tempdir /;
my $seq1ID = param("seq1Id");
my $seq2ID = param("seq2Id");

my $matixName = param("distanceMatrix") or die ("No distance matrix");
my $gapOpen = param("gapOpenPenalty")or die ("No go");
my $gapExtend = param("gapExtendPenalty")or die ("No ge");

my $alignmentType = param("alignmentType")or die ("No alignment type");
my $alignmentAlgo = param("alignmentAlgorithm")or die ("No alignment algorithm");

my $calcSSAA = param("calculateSSAA");
my $fixedPoint = param("fixedPoint");
carp "Fixed point " . $fixedPoint;

my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";

#getMatrixFromDatabase($db, $matrixName, $outputFile) get QUASAR matrix from
sub getMatrixFromDatabase {
	my $db = $_[0];
	my $matrixName = $_[1];
	my $outputFile = $_[2];

	my $query = $db->prepare("SELECT QUASAR FROM Matrices WHERE LCASE(Matrices.Name) = LCASE(?)");
	$query->execute($matrixName);
	my $row = $query->fetchrow_hashref();
	if(not defined $row) {
		die "Matrix with name $matrixName can't be found in database";
	}
	my $quasar = $row->{QUASAR};
	#Write it to a files
	open (OUTFILE, ">$outputFile");
	print OUTFILE $quasar
	close(OUTFILE);
}

sub getSequenceById {
	my $db = $_[0];
	my $id = $_[1];
	#
	my $seq = "";
	#Parse it
	if($id =~ m/^pdb:(.+)$/) {
		$seq = get("http://www.pdb.org/pdb/files/fasta.txt?structureIdList=$id");
	} elsif($id =~ m/^uniprot:(.+)$/){
		$seq = get("http://www.uniprot.org/uniprot/$id.fasta");
	}
	elsif($id =~ m/^mysql:(.+)$/){
		my $query = $db->prepare("SELECT Seq.Seq FROM Seq WHERE Seq.Name = ?");
		$query->execute($id);
		my $row = $query->fetchrow_hasref();
		if(not defined $row) {die "Matrix with name $matrixName can't be found in database";}
		$seq = $row->{Name};
	}
	die "Seq not found" if $seq eq "";
	return $seq;
}

my $outputPath = tempdir();
carp "Temp output path is $outputPath";

my $seq1 = getSequenceById($seq1ID);
my $seq2 = getSequenceById($seq1ID);
open (OUTFILE, ">$outputFile/sequences.seqlib");
print (OUTFILE, "$seq1ID:$seq1\n$seq2ID:$seq2");
close(OUTFILE);

open (OUTFILE, ">$outputFile/seqPair.pairs");
print (OUTFILE, "$Seq1ID $Seq2ID");
close(OUTFILE);

my $matrix = getMatrixFromDatabase($db, $matrixName, "$outputPath/$matrixName");
my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/Solution4/finaljars";
my $executeJar = `bash -c 'java -jar $jarPath/alignment.jar --go $gapOpen --ge $gapExtend --dpmatrices "$outputPath/$matrixName" --pairs "$outputFile/seqPair.pairs" --seqlib "$outputFile/sequences.seqlib" -m $matrixName --mode $alignmentType --format "html" > alignmentout.txt'`;



