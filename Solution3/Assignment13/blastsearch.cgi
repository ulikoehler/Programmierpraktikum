#!/usr/bin/perl
use strict;
use File::Temp qw/ tempfile tempdir /;
use CGI;
use File::Copy;
use HTML::Entities;
my $cgi = new CGI;
#Create a temporary dir
#my $tempdir = tempdir();
print $cgi->header(-type => 'text/html');
my $tempdir = "/tmp/ulix";
`mkdir -p $tempdir`;
#We do generate HTML
#Save the query sequence in a file
my $query = $cgi->param("query");
my $queryFilename = $tempdir."/query.fa";
open(QUERYOUTFILE, ">".$queryFilename);
print QUERYOUTFILE $query;
close(QUERYOUTFILE);
#Get the filename
my $dbTempFilename = $cgi->param("database");
#Save the database query to the
my $dbFilename = $tempdir."/database.fa";
#Copy the temp file to the database filename
copy($dbTempFilename,$dbFilename) or die "Copy failed: $!";
#Create the BLAST DB + index
`makeblastdb -in $dbFilename -hash_index -dbtype prot  2>>&1 >> makeblastdb.log`;
#Do the query
my $queryOutput = `blastp -db $dbFilename -query $queryFilename`;
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
