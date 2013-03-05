#!/usr/bin/perl -w

# initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use File::Temp qw/ tempfile tempdir /;

my $model = param("model");				# file
my $seq = param("seq");					# file
my $maf = param("maf");					# file
my $probabilities = param("probabilities") || 0;	# boolean
my $avgPost = param("avgPost") || 0;			# boolean
my $stdPost = param("stdpost") || 0;			# boolean
my $avgValue = param("avgValue") || 0;			# double for avgPost
my $stdValue = param("stdValue") || 0;			# double for stdPost


my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/Solution4/jar";
my $format = "html"

my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";

sub getGORModel {
	my $db = $_[0];
	my $gorName = $_[1];
	my $outputPath = $_[2];

	my $query = $db->prepare("SELECT Data FROM GORModel WHERE LCASE(Name) = LCASE(?)");
	$query->execute($matrixName);
	my $row = $query->fetchrow_hashref();
	if(not defined $row) {
		die "GOR Model with name $matrixName can't be found in database";
	}
	my $quasar = $row->{Data};
	#Write it to a files
	open (OUTFILE, ">$outputPath");
	print OUTFILE $quasar;
	close(OUTFILE);
}

my($fh, $modelFile) = tempfile();
getGORModel($db, $model, $modelFile);


# building java query
carp "Missing model file!" if !defined $model;
my $jarQuery = "--model $modelFile --format $format";

# seq or maf
if(defined $seq) {
  $jarQuery = "$jarQuery --seq $seq";
} elsif(defined $maf) {
  $jarQuery = "$jarQuery --maf $maf";
} else {
  carp "Missing sequence or aligment!";
}

# probabilities
$jarQuery = "$jarQuery --probabilities" if $probabilities;

# avgPost
if $avgPost {
  carp "Missing or invalid value for postprocessing!" if !isNumeric $avgValue;
  $jarQuery = "$jarQuery --avgPost $avgPost";
}

# stdPost
if $stdPost {
  carp "Missing or invalid value for postprocessing!" if !isNumeric $stdValue;
  $jarQuery = "$jarQuery --stdPost $stdPost";
}

my $jarOutput = `bash -c 'java -jar $jarPath/predict.jar $jarQuery`;

my $q = new CGI;
print $q->header;
print $q->$jarOutput;
    
sub isNumeric {
  return 1 if ($_[0] =~ /^([+-]?)(?=\d&\.\d)\d*(\.\d*)?([Ee]([+-]?\d+))?$/);
  return 0;
}
