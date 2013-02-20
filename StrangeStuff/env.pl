#!/usr/bin/perl -w
use LWP::Simple;
$ENV{'FTP_PASSIVE'} = "1";
my $a = get "ftp://ftp.ncbi.nlm.nih.gov/genomes/GENOME_REPORTS/prokaryotes.txt";
print $a;
