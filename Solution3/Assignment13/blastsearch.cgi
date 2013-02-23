#!/usr/bin/perl
use strict;
use File::Temp qw/ tempfile tempdir /;
use CGI::Carp qw(fatalsToBrowser);
use CGI;
use File::Copy;
use HTML::Entities;
use CGI qw(:standard);
#Create a temporary dir
my $tempdir = tempdir();
carp "Tempdir: $tempdir";
print header('text/html');
#my $tempdir = "/tmp/ulix";
#`mkdir -p $tempdir`;
#We do generate HTML
#Save the query sequence in a file
my $query = param("query") || ">sp|P00846|ATP6_HUMAN ATP synthase subunit a OS=Homo sapiens GN=MT-ATP6 PE=1 SV=1\nMNENLFASFIAPTILGLPAAVLIILFPPLLIPTSKYLINNRLITTQQWLIKLTSKQMMTM\nHNTKGRTWSLMLVSLIIFIATTNLLGLLPHSFTPTTQLSMNLAMAIPLWAGTVIMGFRSK\nIKNALAHFLPQGTPTPLIPMLVIIETISLLIQPMALAVRLTANITAGHLLMHLIGSATLA\nMSTINLPSTLIIFTILILLTILEIAVALIQAYVFTLLVSLYLHDNT";
my $queryFilename = $tempdir."/query.fa";
open(QUERYOUTFILE, ">".$queryFilename);
print QUERYOUTFILE $query;
close(QUERYOUTFILE);
#Get the filename
my $dbTempFilename = param("database") || "../mgenitalium.fa";
#Save the database query to the
my $dbFilename = $tempdir."/database.fa";
#Copy the temp file to the database filename
copy($dbTempFilename,$dbFilename) or die "Copy failed: $!";
#Create the BLAST DB + index
my $bindir = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/bin/";
`bash -c '$bindir/makeblastdb -in $dbFilename -hash_index -dbtype prot  2>> makeblastdb.log >> makeblastdb.log'`;
#Do the query
my $queryOutput = `bash -c '$bindir/blastp -db $dbFilename -query $queryFilename'`;
$queryOutput = encode_entities($queryOutput);
#Print the HTML prototype
print <<"HTML";
<html>
<head>
<title>Fileupload</title>
</head>
<body bgcolor="#FFFFFF">
<pre>
$queryOutput
</pre>
</body>
</html>
HTML
