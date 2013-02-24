#!/usr/bin/perl -w
use strict;
use DBI;
use CGI qw(:standard);
#The prosite pattern is supplied as CLI arg
my $prositePattern = param("pattern"); #The content of this variable is conv to regex later!
my $originalPrositePattern = $prositePattern;
#Convert the prosite pattern to a regular expression
$prositePattern =~ s/x/\./g; #Replace x by . (matches any character)
#Process exclusion specifiers like {GA}
$prositePattern =~ s/\{(\w+)\}/\[\^$1\]/g; #Replace {3} by [^3]
#Replace length specifiers. Split into two regexes to improve readability
$prositePattern =~ s/\((\d+)\)/\{$1\}/g; #Replace (3) by {3}
$prositePattern =~ s/\((\d+,\d+)\)/\{$1\}/g; #Replace (3,5) by {3,5}
#We don't need to replace something like [NHG], it's already a regex
#Finally, remove minuses
$prositePattern =~ s/[-]//g;
#Add a global group to be able to match it later
#Print the SQL statement
my $db =  DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";	
my $query = $db->prepare("SELECT Seq.Name, DB.Name AS DBName, Organism.Name AS OName FROM Seq
INNER JOIN Organism ON Organism.Id = Seq.OrganismId
INNER JOIN Source ON Source.SeqId = Seq.Id
INNER JOIN DB ON DB.Id = Source.DBId WHERE Seq.Seq REGEXP ?;");
$query->execute($prositePattern);
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
 <h1>Keyword search</h1>
 </div>
 <div id="body">
  <h2>Results of search for keyword $originalPrositePattern</2>
  <table><tr><td><b>Name</b></td><td><b>Organism</b></td></tr>
EOHTML
;
#Print the results
my $result = undef;
while ($result = $query->fetchrow_hashref() ) {
	#Create a link only if it's in Swissprot
	my $id = $result->{Name};
	if($result->{DBName} eq "SwissProt") {
		print "<tr><td><a href=\"http://www.uniprot.org/uniprot/$id\">$id</a></td><td>".$result->{OName}."</td></tr>";
	} else {
		print "<tr><td>$id</td><td>".$result->{OName}."</td></tr>";
	}
}
print <<"EOHTML"
  </table>
</body>
</html>
EOHTML
;
$db->disconnect();