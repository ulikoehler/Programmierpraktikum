#!/usr/bin/perl -w
use strict;
use LWP::Simple;
use HTML::Entities;
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
print header('text/html');
#Parse the CGI arg
my $pdbId = param("pdbid");
#Download it
my $url = "http://www.pdb.org/pdb/download/downloadFile.do?fileFormat=pdb&compression=NO&structureId=" . $pdbId;
my $pdbData = get $url;
#Handle errors
print header(-status=>404) unless $pdbId;
die"Can't fetch PDB data for PDB ID $pdbId\n" unless $pdbData;
exit unless $pdbData;
#Print the data (only executed if not error occured before
$pdbData = encode_entities($pdbData);
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
 <h1>PDB Data retrieval (PDB format)</h1>
 </div>
 <div id="body">
  <h2>Your request for <a href="http://www.rcsb.org/pdb/explore/explore.do?structureId=$pdbId">$pdbId</a> yielded:</2>
  <pre style="font-size: 50%; text-decoration: none;font-weight:normal;color:#333;">
  $pdbData
  </pre>
</body>
</html>
EOHTML
