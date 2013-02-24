#!/usr/bin/perl -w
use strict;
use DBI;
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
#The search pdb id is supplied as CLI arg
my $pdbId1 = param("pdbid1");
die "PDB ID 1 must consist of 4+ alphanum chars" unless $pdbId1 =~ m/^[A-Z0-9]{4,}$/;
my $pdbId2 = param("pdbid2");
die "PDB ID 2 must consist of 4+ alphanum chars" unless $pdbId2 =~ m/^[A-Z0-9]{4,}$/;
#Print the SQL statement
my $db =  DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";	
##
#Alignment queries
##
#Query 1: Where the pdbid is the 'main' protein
my $aquery = $db->prepare("SELECT DISTINCT * FROM SecStructAlign WHERE FromDBId LIKE ? AND ToDBId LIKE ? AND EntryType = 'Alignment'");
$aquery->execute(lc($pdbId1)."%", lc($pdbId2)."%");
##
#Structure queries
##
print header("text/html");
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
 <h1><a href="http://tardis.nibio.go.jp/homstrad/">HOMSTRAD</a> search</h1>
 </div>
 <div id="body">
  <h2>Results of search for alignment of PDB ID $pdbId1 and $pdbId2</2>
  <h4>Alignments</h4>
  <table border="1" ><tr><td><b>Protein1</b></td><td><b>Protein2</b></td><td><b>Alignment</b></td></tr>
EOHTML
;
#Print the alignments
my $result = undef;
my $foundAnything = 0;
while ($result = $aquery->fetchrow_hashref() ) {
	$foundAnything = 1;
	my $prot1 = uc($result->{FromDBId});
	my $prot2 = uc($result->{ToDBId});
	my $alignment = uc($result->{Content});
	print "<tr><td>$prot1</td><td>$prot2</td><td>$alignment</td></tr>";
}
unless($foundAnything) {
	print "<tr><td colspan=\"3\">Can't find anything</td></tr>";
}
print <<"EOHTML"
</table>
</body>
</html>
EOHTML
;
$db->disconnect();