#!/usr/bin/perl -w
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);

my $seq1ID = param("seq1Id");
my $seq2ID = param("seq2Id");

my $alignmentType = param("alignmentType");
my $alignmentAlgo = param("alignmentAlgorithm");

my $calcSSAA = param("calculateSSAA");
