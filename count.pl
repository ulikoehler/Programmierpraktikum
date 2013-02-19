#!/usr/bin/perl -w
use strict;
my $text = "";
while (<STDIN>) {
	$text = $text . $_;
}
my @count = $text =~ /GC/g;
print scalar(@count);