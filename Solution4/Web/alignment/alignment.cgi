#!/usr/bin/perl -w
use strict;
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use DBI;
use File::Copy;
use File::Temp qw/ tempfile tempdir /;
my $seq1ID = param("alignmentSeq1Id") or die ("No sequence 1 ID");
my $seq2ID = param("alignmentSeq2Id") or die ("No sequence 2 ID");

my $matrixName = param("distanceMatrix") or die ("No distance matrix");
my $gapOpen = param("gapOpenPenalty") or die ("No go");
my $gapExtend = param("gapExtendPenalty") or die ("No ge");

my $alignmentType = param("alignmentType") or die ("No alignment type");
my $alignmentAlgo = param("alignmentAlgorithm") or die ("No alignment algorithm");

my $calcSSAA = param("calculateSSAA");
my $gorModelName = param("gormodel");

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

sub getGORModel {
	my $db = $_[0];
	my $gorName = $_[1];
	my $outputPath = $_[2];

	my $query = $db->prepare("SELECT Data FROM GORModels WHERE LCASE(Name) = LCASE(?)");
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

my $curtime = time();
my $outputPath = "/home/k/koehleru/public_html/propra/aligntmp/" . $curtime;
my $urlPath = "aligntmp/".$curtime;
`mkdir -p $outputPath`;
carp "Outputting to $outputPath";
my $dpPath = "$outputPath/dp";
`mkdir -p $dpPath`;
my $fpaDir = "$outputPath/fpa";
`mkdir -p $fpaDir`;
my $dpMatricesDir = "$outputPath/dp";
`mkdir -p  $dpMatricesDir`;
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

#Write the data files
open (SEQOUT, ">$outputPath/sequences.seqlib");
print SEQOUT "$seq1ID:$seq1\n";
print SEQOUT "$seq2ID:$seq2\n";
close(SEQOUT);

open (OUTFILE, ">$outputPath/seqPair.pairs");
print OUTFILE "$seq1ID\t$seq2ID";
close(OUTFILE);

my $matrix = getMatrixFromDatabase($db, $matrixName, "$outputPath/$matrixName");
my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/jar";

#carp "/usr/lib64/biojava/bin/java -jar $jarPath/align.jar --go $gapOpen --ge $gapExtend --pairs $outputPath/seqPair.pairs --seqlib $outputPath/sequences.seqlib -m $outputPath/$matrixName --mode $alignmentType --fixedpointalignment $fpaDir --format html > alignmentout.txt";
print header();

my $ssaaOpt = "";
if($gorModelName) {
  print "<i>Secondary structure predicted using GOR Model $gorModelName</i><br/>";
  carp "Using GOR prediction on model $gorModelName";
  getGORModel($db, $gorModelName, "$outputPath/gormodel.txt");
  open (SEQOUT, ">$outputPath/sequences.fa");
  print SEQOUT ">$seq1ID\nAS $seq1\n";
  print SEQOUT ">$seq2ID\nAS $seq2\n";
  close(SEQOUT);
  `/usr/lib64/biojava/bin/java -jar $jarPath/predict.jar -m $outputPath/gormodel.txt -s $outputPath/sequences.fa > $outputPath/gorpred.txt`;
  $ssaaOpt = "-q $outputPath/gorpred.txt";  
}
#Option
my $algoOpt = "";
$algoOpt = "--nw" if $alignmentAlgo eq "NeedlemanWunsch";
#Calculate the HTML alignment
my $cli = "/usr/lib64/biojava/bin/java -jar $jarPath/align.jar $algoOpt --go $gapOpen --ge $gapExtend --pairs $outputPath/seqPair.pairs --seqlib $outputPath/sequences.seqlib -m $outputPath/$matrixName --mode $alignmentType --fixedpointalignment $fpaDir --format html $ssaaOpt --dpmatrices $dpMatricesDir";
my $output = `bash -c '$cli'`;
#Do the same for ALI output (for validation) without any extra stuff
$cli = "/usr/lib64/biojava/bin/java $algoOpt -jar $jarPath/align.jar --go $gapOpen --ge $gapExtend --pairs $outputPath/seqPair.pairs --seqlib $outputPath/sequences.seqlib -m \"$outputPath/$matrixName\" --mode $alignmentType --format ali";
#Write the ALI output to a file
my $aliOutput = `bash -c '$cli' 2>&1`;
my $aliFile = "$outputPath/alignment.ali";
open (ALIOUT, ">$aliFile");
print ALIOUT "$aliOutput";
close(ALIOUT);
#Prin the alignment
print $output;
#
#Display the fixed-point alignment
#
opendir my($fpaDh), $fpaDir or die "Couldn't open dir '$fpaDir': $!";
my @fpaFiles = readdir $fpaDh;
closedir $fpaDh;
foreach my $file (@fpaFiles) {
  if($file eq ".." or $file eq ".") {
    next;
  }
  #carp "cp -f $fpaDir/$file /home/k/koehleru/public_html/propra/fpa/$file";
  #copy("$fpaDir/$file", "/home/k/koehleru/public_html/propra/fpa/$file");
#   `cp -f "$fpaDir/$file" "/home/k/koehleru/public_html/propra/fpa/"`;
  #copy("$fpaDir/$file", "/home/k/koehleru/public_html/propra/fpa/");
  #print "<br/><a href=\"$urlPath/fpa/$file\">Fixed point alignment plot (You need to wait ~10 s for technical reasons)</a><br/>"
  print "<br/><a href=\"$urlPath/fpa/$file\"><img src=\"$urlPath/fpa/$file\"></img></a><br/>"
}
#
#Display the DP matrices
#
opendir my($dpFh), $dpMatricesDir or die "Couldn't open dir '$dpMatricesDir': $!";
my @dpFiles = readdir $dpFh;
closedir $dpFh;
print "<b>Dynamic programming matrices:</b><br/>\n";
foreach my $dpFile (@dpFiles) {
  if($dpFile eq ".." or $dpFile eq ".") {
    next;
  }
  if($dpFile =~ m/\.html/) {
    print "<br/><a href=\"$urlPath/dp/$dpFile\" target=\"_blank\">$dpFile</a><br/>"
  }
}
#Display link to alignment files
print "<br/><a href=\"$urlPath/alignment.ali\">Alignment in ALI format</a><br/>\n";
#
# Display options to validate the alignment
#
print <<"EOHTML"
<h3>Validate this alignment</h3><br/>
<form action="validation/validate_ali.cgi" method="post" enctype="multipart/form-data">
  <b>Reference alignment:</b><br/>
  <textarea id="reference" name="reference" style="width:100%" rows="12"></textarea><br/>
  <input type="hidden" name="aliFile" value="$aliFile"/>
  <input type="submit" class="button"/>
</form>
EOHTML
;
$db->disconnect();

