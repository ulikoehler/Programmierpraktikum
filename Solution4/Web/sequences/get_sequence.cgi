#!/usr/bin/perl -w
use strict;
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use DBI;
use File::Copy;
use File::Temp qw/ tempfile tempdir /;
my $seqID = param("id");

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

sub getFirstFASTASequence {
  my $data = $_[0];
  my $seq = "";
  my $alreadyReadHeader = 0;
  for (split /^/, $data) {
	  my $line = $_;
	  if($line =~ m/^>/) {
	    if($alreadyReadHeader) {
	      last;
	    } else {
	      $alreadyReadHeader = 1;
	      next;
	     }
	  } else {
	    chomp $line;
	    $seq = $seq.$line;
	    chomp $seq;
	  }
  }
  return $seq;
}

sub getSequenceById {
	my $db = $_[0];
	my $id = $_[1];
	#
	my $seq = "";
	#Parse it
	use LWP::Simple;
	if($id =~ m/^pdb:(.+)$/) {
		$seq = get("http://www.pdb.org/pdb/files/fasta.txt?structureIdList=".$1);
		$seq = getFirstFASTASequence($seq);
		#die "http://www.pdb.org/pdb/files/fasta.txt?structureIdList=".$1;
	} elsif($id =~ m/^uniprot:(.+)$/){
		$seq = get("http://www.uniprot.org/uniprot/" . $1 . ".fasta");
		$seq = getFirstFASTASequence($seq);
	} elsif($id =~ m/^mysql:(.+)$/){
		my $query = $db->prepare("SELECT Seq.Seq FROM Seq WHERE Seq.Name = ?");
		$query->execute($1);
		my $row = $query->fetchrow_hashref();
		if(not defined $row) {die "Sequence with ID $id can't be found in database";}
		$seq = $row->{Seq};
	}
	die "Seq $id not found" unless $seq;
	return $seq;
}

print header("text/plain");
my $seq1 = getSequenceById($db, $seqID);
print $seq1;
$db->disconnect();

