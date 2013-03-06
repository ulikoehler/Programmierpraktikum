#!/usr/bin/perl -w
#Initialize CGI parser
use CGI::Carp qw(fatalsToBrowser);
use File::Temp qw/ tempfile tempdir /;
use CGI qw(:standard);

my $alignment = param("alignment");
my $reference = param("reference");
print header();

carp "ALI: $alignment\n";

my $tempdir = tempdir();
my $alignPath = $tempdir."/align.txt";
my $refPath = $tempdir."/ref.txt";
#print $tempdir."\n";
print $data;
#my $alignmentData = readData($alignments);
open (ALIGNOUT, ">$alignPath");
print ALIGNOUT $alignment;
close(ALIGNOUT );

open (OUTFILE, ">$refPath");
print OUTFILE $reference;
close(OUTFILE);

my $html = "html";
my $summary = "summary";
my $detailed = "detailed";
my $jarPath = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/jar";
#carp "XXXTEMPDIR: $tempdir\n";
carp "bash -c 'cd $tempdir && /usr/lib64/biojava/bin/java -jar $jarPath/validateAli.jar -a $alignPath -r $refPath -s $summary -d $detailed -f txt'";
my $result = `bash -c 'cd $tempdir && /usr/lib64/biojava/bin/java -jar $jarPath/validateAli.jar -a $alignPath -r $refPath -s $summary -d $detailed -f txt'`;

#Read the result
my $detailed = "";
open (IN, "<$tempdir/detailed");
while (<IN>){
 $detailed .= $_;
}
close(IN);


print $detailed;#"<!Doctype><html>result: $alignPath</html>";
