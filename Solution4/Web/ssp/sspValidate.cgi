#! /usr/bin/perl

# initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use DBI;
use File::Temp qw/ tempfile tempdir /;

my $model = param("model");				# file

my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/Solution4/finaljars";

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

my($fh, $modelFile) = tempfile();
getGORModel($db, $model, $modelFile);

carp "Executing bash -c '/usr/lib64/biojava/bin/java -jar $jarPath/predict.jar --model $modelFile --seq CB513.DB > data'\n";
my $jarPredict = `bash -c '/usr/lib64/biojava/bin/java -jar $jarPath/predict.jar --model $modelFile --seq CB513.DB > data'`;

carp "Executing bash -c '/usr/lib64/biojava/bin/java -jar $jarPath/validateGor.jar --predictions data --dssp-file CB513.DB --format txt -s from/summary -d from/detail'";
my $jarValidate = `bash -c '/usr/lib64/biojava/bin/java -jar $jarPath/validateGor.jar --predictions data --dssp-file CB513.DB --format txt -s from/summary -d from/detail'`;

carp "Executing bash -c '/usr/lib64/biojava/bin/java -jar $jarPath/validateGor.jar --predictions data --dssp-file CB513.DB --format txt -s from/summary -d detail'";

carp "Executing bash -c '/usr/lib64/biojava/bin/java -jar conv.jar from/ to/";
my $jarValidate = `bash -c '/usr/lib64/biojava/bin/java -jar conv.jar from/ to/'`;

carp "Executing bash -c 'cp to/summary ./data";
my $cp = `cp to/summary ./data`;
carp "Executing bash -c 'plot data";
my $plot = `gnuplot < gPlotProgSingle`;



