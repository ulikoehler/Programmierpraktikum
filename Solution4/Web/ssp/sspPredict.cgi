#!/usr/bin/perl -w

# initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use DBI;
use File::Temp qw/ tempfile tempdir /;

my $model = param("model");				# file
my $gor5Alignment = param("gor5AlignmentField");
my $seqId = param("sspSequence");
my $probabilities = param("probabilities") || 0;	# boolean
my $avgPost = param("avgPost") || 0;			# boolean
my $stdPost = param("stdpost") || 0;			# boolean
my $avgValue = param("avgValue") || 0;			# double for avgPost
my $stdValue = param("stdValue") || 0;			# double for stdPost


my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/jar";
my $format = "html";

my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";

sub getGORModel {
	my $db = $_[0];
	my $gorName = $_[1];
	my $outputPath = $_[2];

	my $query = $db->prepare("SELECT Data FROM GORModels WHERE Name = ?");
	$query->execute($gorName);
	my $row = $query->fetchrow_hashref();
	if(not defined $row) {
		die "GOR Model with name $gorName can't be found in database";
	}
	my $quasar = $row->{Data};
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

my $seq = getSequenceById($db, $seqId);

my($fh, $modelFile) = tempfile();
getGORModel($db, $model, $modelFile);

# building java query
carp "Missing model file!" if !defined $model;
my $jarQuery = "--model $modelFile --format $format";

# seq or maf
if(defined $seq) {
  my($fh, $seqFile) = tempfile();
  open (OUTFILE, ">$seqFile");
  print OUTFILE ">$seqId\n";
  print OUTFILE $seq;
  close(OUTFILE);
  $jarQuery = "$jarQuery --seq $seqFile";
} elsif(defined $gor5Alignment) {
  my($fh, $gor5AlignmentFile) = tempfile();
  open (OUTFILE, ">$gor5AlignmentFile");
  print OUTFILE $gor5AlignmentF;
  close(OUTFILE);
  $jarQuery = "$jarQuery --maf $gor5AlignmentFile";
} else {
  carp "Missing sequence or aligment!";
}

# probabilities
$jarQuery = "$jarQuery --probabilities" if $probabilities;

# avgPost
if ($avgPost) {
  carp "Missing or invalid value for postprocessing!" if !isNumeric $avgValue;
  $jarQuery = "$jarQuery --avgPost $avgPost";
}

# stdPost
if ($stdPost) {
  carp "Missing or invalid value for postprocessing!" if !isNumeric $stdValue;
  $jarQuery = "$jarQuery --stdPost $stdPost";
}

carp "Executing bash -c '/usr/lib64/biojava/bin/java -jar $jarPath/predict.jar $jarQuery'\n";
my $jarOutput = `bash -c '/usr/lib64/biojava/bin/java -jar $jarPath/predict.jar $jarQuery'`;

print header();
print $jarOutput . "\n";
    
sub isNumeric {
  return 1 if ($_[0] =~ /^([+-]?)(?=\d&\.\d)\d*(\.\d*)?([Ee]([+-]?\d+))?$/);
  return 0;
}
