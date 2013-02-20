#!/usr/bin/perl -w
use LWP::Simple;
my $a = get "ftp://ftp.ncbi.nlm.nih.gov/genomes/GENOME_REPORTS/prokaryotes.txt"
print $a;
