#! /usr/bin/perl5.14.2 -wl
#warnings, newlines
use strict;
use LWP::Simple;

print "Enter query for uniprot, you will receive the number of results.";
my $line = <STDIN>;

my $html = get("http://www.uniprot.org/uniprot/?query=".$line);
my @chars = split("", $html);
my $subs;
if($html =~ m/of <strong>.+<\/strong> result/){
	my $regexLocation = $-[0];
	for(my $i=$regexLocation+10;$i<$regexLocation+30;$i++){
		$subs.=$chars[$i];
	}
	
}
$html =~ m/of <strong>.+<\/strong> result/;
#print $-[0]." bis ".$+[0];
#print $subs;
$subs =~ m/(\d+,?)+/;
print "Suche nach ".$line."ergibt: ".substr($subs,$-[0],$-[0]+$+[0]-2)." Ergebnisse.";
