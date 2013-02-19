#! /usr/bin/perl5.14.2 -wl
#warnings, newlines
use strict;

my $RNA;
print "You entered ".$ARGV[0]."\n";
my @chars = split("", $ARGV[0]);
for(my $i=0;$i<length($ARGV[0]);$i++){
	if($chars[$i] eq "T"){
		$RNA.="U";
	}
	else{
		$RNA.=$chars[$i];
	}
}
print "RNA: ".$RNA;
