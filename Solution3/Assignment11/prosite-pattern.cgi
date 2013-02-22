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
my $query = $db->prepare("SELECT * FROM `Seq` WHERE Seq.Seq REGEXP ?;\n");
$query->execute($prositePattern);
print header('text/plain');
print "abc";
$db->close();