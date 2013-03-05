#!/usr/bin/perl -w
#Initialize CGI parser
#use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use File::Temp qw/ tempfile tempdir /;

use CGI; # Modul fuer CGI-Programme

my $cgi = new CGI; # neues Objekt erstellen

# Content-type fuer die Ausgabe
print $cgi->header();

# die datei-daten holen
my $file = $cgi->param("alignments");
carp "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX " . $file;
my $reference = $cgi->param("reference");

# dateinamen erstellen und die datei auf dem server speichern
my $fname = "alignments";#'file_'.$$.'_'.$ENV{REMOTE_ADDR}.'_'.time;
open DAT,'>'.$fname or die 'Error processing file: ',$!;

# Dateien in den Binaer-Modus schalten
binmode $file;
binmode DAT;

my $data;
while(read $file,$data,1024) {
  print DAT $data;
}
close DAT;

# dateinamen erstellen und die datei auf dem server speichern
my $fname2 = "reference";#'file_'.$$.'_'.$ENV{REMOTE_ADDR}.'_'.time;
open DAT2,'>'.$fname2 or die 'Error processing file: ',$!;

# Dateien in den Binaer-Modus schalten
binmode $reference;
binmode DAT2;

my $dataRef;
while(read $reference,$dataRef,1024) {
  print DAT $dataRef;
}
close DAT;
########################



print $cgi->header();
my $tempdir = tempdir();
my $alignPath = $tempdir."/align.txt";
my $refPath = $tempdir."/ref.txt";
#print $tempdir."\n";
print $data;
#my $alignmentData = readData($alignments);
carp "ADADADA : $alignmentData\n";
open (ALIGNOUT, ">$alignPath");
print ALIGNOUT $data;
close(ALIGNOUT );

open (OUTFILE, ">$refPath");
print OUTFILE $dataRef;
close(OUTFILE);

my $html = "html";
my $summary = "summary";
my $detailed = "detailed";
carp "bash -c '/usr/lib64/biojava/bin/java -jar validateAli.jar -a $alignPath -r $refPath -s $summary -d $detailed -f $html'";
my $result = `bash -c '/usr/lib64/biojava/bin/java -jar validateAli.jar -a $alignPath -r $refPath -s $summary -d $detailed -f $html'`;
print $alignPath;#"<!Doctype><html>result: $alignPath</html>";
