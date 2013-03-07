#!/usr/bin/perl -w
use strict;
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::Simple;
use DBI;
use File::Temp qw/ tempfile tempdir /;

my $reference = param("reference");
my $aliFile = param("aliFile");
#Write the reference to a file
my ($refFh, $referenceFile) = tempfile();
open (REFOUT, ">$referenceFile");
print REFOUT "$reference";
close(REFOUT);
#Call it
my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/jar";
my ($sumFh, $summaryFile) = tempfile();
my ($sumFh, $detailsFile) = tempfile();
my $cli = "java -jar validateAli.jar -a $aliFile -r $referenceFile -s $summaryFile -d $detailsFile --format txt";
my $output = `bash -c '$cli'`;
#Write the details
print header();
print `cat $detailsFile`;