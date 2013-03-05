#!/usr/bin/perl -w
#use strict;
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use File::Temp qw/ tempfile tempdir /;

my $predict = param("predict");
my $dssp = param("dssp");

sub readData{
	my $datafile = $_[0];
	my $processedData = "";
	binmode $datafile;
	my $data = "";
	while(read $datafile,$inputdata,1024) {
	  $data = $data.$inputdata;
	}
	return $data;
}

#Remove
for (split /^/, $seqData) {
	chomp $_;
	$processedData = $processedData.trim($_) if $_ !~ m/^>/;
	chomp $processedData;
}

sub trim {		# trim a string
  my $string = $_[0];
  $string =~ s/^\s+//;
  $string =~ s/\s+$//;
  return $string;
}

my $tempdir = tempdir();
my $alignPath = $tempdir."/align.txt";
my $refPath = $tempdir."/ref.txt";

open (OUTFILE, ">$alignPath");
print OUTFILE readData($predict);
close(OUTFILE);

open (OUTFILE, ">$refPath");
print OUTFILE readData($dssp);
close(OUTFILE);

my $html = "html";
my $summary = "summary";
my $detailed = "detailed";
print header();
my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/jar";
my $result = `bash -c 'java -jar $jarPath/validateGor.jar -p $alignPath -r $refPath -s $summary -d $detailed -f $html'`;
print "<!Doctype><html>result: $result</html>";
