#!/usr/bin/perl -w
use strict;
#Ask the user to input the motif
print "Please enter search motif: ";
my $motif = <STDIN>;
chomp $motif;
#Assemble the text from stdin
my $text = "";
while (<>) {
	if(not ($_ =~ m/^>/)) {
		$text = $text . uc($_);
	}
}
#Remove N (not known) pseudobases from the text
$text =~ s/N//g;
#Count how many times the motif occured
my @count = $text =~ /$motif/g;
#Print the number of matches
print scalar(@count);
