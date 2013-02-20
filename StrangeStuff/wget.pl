#!/usr/bin/perl -w
use LWP::Simple;
my $a = `wget -qO- ftp://ftp.ncbi.nlm.nih.gov/genomes/GENOME_REPORTS/prokaryotes.txt`;
print $a;
