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
my $query = param("query");
die "No query specified\n" unless $query;
my $queryFilename = $tempdir."/query.fa";
open(QUERYOUTFILE, ">".$queryFilename);
print QUERYOUTFILE $query;
close(QUERYOUTFILE);
#Get the filename
my $dbTempFilename = param("database");
#Save the database query to the temporary file
my $dbFilename = $tempdir."/database.fa";
#Copy the temp file to the database filename
copy($dbTempFilename,$dbFilename) or die "Copy failed: $!";
#Create the BLAST DB + index
my $bindir = "/home/proj/biocluster/praktikum/bioprakt/progprakt4/bin/";
`bash -c '$bindir/makeblastdb -in $dbFilename -hash_index -dbtype $type  2>> makeblastdb.log >> makeblastdb.log'`;
#Do the query
my $queryOutput = `bash -c '$bindir/blastp -db $dbFilename -query $queryFilename'`;
$queryOutput = encode_entities($queryOutput);
#Remove the temporary stuff
`rm -rf $tempdir`;
#Print the HTML prototype
print <<"EOHTML"
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Propra G4</title>
<style type="text/css" media="screen">
body { background: #e7e7e7; font-family: Verdana, sans-serif; font-size: 11pt; }
#page { background: #ffffff; margin: 50px; border: 2px solid #c0c0c0; padding: 10px; }
#header { background: #4b6983; border: 2px solid #7590ae; text-align: center; padding: 10px; color: #ffffff; }
#header h1 { color: #ffffff; }
#body { padding: 10px; }
span.tt { font-family: monospace; }
span.bold { font-weight: bold; }
a:link { text-decoration: none; font-weight: bold; color: #C00; background: #ffc; }
a:visited { text-decoration: none; font-weight: bold; color: #999; background: #ffc; }
a:active { text-decoration: none; font-weight: bold; color: #F00; background: #FC0; }
a:hover { text-decoration: none; color: #C00; background: #FC0; }
</style>
</head>
<body>
<div id="page">
 <div id="header">
 <h1>Custom BLAST</h1>
 </div>
 <div id="body">
  <h2>Your custom BLAST search yielded:</2>
  <pre>
  $queryOutput
  </pre>
</body>
</html>
EOHTML
;
