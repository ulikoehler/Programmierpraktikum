#!/usr/bin/perl -w
use strict;
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use DBI;
use File::Temp qw/ tempfile tempdir /;
my $seq1ID = param("alignmentSeq1Id") or die ("No sequence 1 ID");
my $seq2ID = param("alignmentSeq2Id") or die ("No sequence 2 ID");

my $matrixName = param("distanceMatrix") or die ("No distance matrix");
my $gapOpen = param("gapOpenPenalty") or die ("No go");
my $gapExtend = param("gapExtendPenalty") or die ("No ge");

my $alignmentType = param("alignmentType") or die ("No alignment type");
my $alignmentAlgo = param("alignmentAlgorithm") or die ("No alignment algorithm");

my $calcSSAA = param("calculateSSAA");
my $fixedPoint = param("fixedPoint");
carp "Fixed point " . $fixedPoint;


my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";

#getMatrixFromDatabase($db, $matrixName, $outputPath) get QUASAR matrix from
sub getMatrixFromDatabase {
	my $db = $_[0];
	my $matrixName = $_[1];
	my $outputPath = $_[2];

	my $query = $db->prepare("SELECT QUASAR FROM Matrices WHERE LCASE(Matrices.Name) = LCASE(?)");
	$query->execute($matrixName);
	my $row = $query->fetchrow_hashref();
	if(not defined $row) {
		die "Matrix with name $matrixName can't be found in database";
	}
	my $quasar = $row->{QUASAR};
	#Write it to a files
	open (OUTFILE, ">$outputPath");
	print OUTFILE $quasar;
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
		$query->execute($1);
		my $row = $query->fetchrow_hashref();
		if(not defined $row) {die "Sequence with ID $id can't be found in database";}
		$seq = $row->{Seq};
	}
	die "Seq $id not found" unless $seq;
	return $seq;
}

my $outputPath = tempdir();
carp "Outputting to $outputPath";
my $dpPath = `mkdir $outputPath/dp`;
carp "Temp output path is $outputPath";


my $seq1 = getSequenceById($db, $seq1ID);
my $seq2 = getSequenceById($db, $seq2ID);

#Split the sequence IDs
if($seq1ID =~ m/:(.+)$/){
  $seq1ID = $1;
}
if($seq2ID =~ m/:(.+)$/){
  $seq2ID = $1;
}
carp "S1 S2 $seq1ID $seq2ID";

#Write the data files
open (SEQOUT, ">$outputPath/sequences.seqlib");
print SEQOUT "$seq1ID:$seq1\n";
print SEQOUT "$seq2ID:$seq2\n";
close(SEQOUT);

open (OUTFILE, ">$outputPath/seqPair.pairs");
print OUTFILE "$seq1ID $seq2ID";
close(OUTFILE);

my $matrix = getMatrixFromDatabase($db, $matrixName, "$outputPath/$matrixName");
my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/jar";

carp 'java -jar $jarPath/align.jar --go $gapOpen --ge $gapExtend --pairs "$outputPath/seqPair.pairs" --seqlib "$outputPath/sequences.seqlib" -m "$outputPath/$matrixName" --mode $alignmentType --format html > alignmentout.txt';

print header();
my $output = `bash -c 'java -jar $jarPath/align.jar --go $gapOpen --ge $gapExtend --pairs "$outputPath/seqPair.pairs" --seqlib "$outputPath/sequences.seqlib" -m "$outputPath/$matrixName" --mode $alignmentType --format html > alignmentout.txt'`;
#
print "Alignment output:\n";
print $output;
$db->disconnect();

