#!/usr/bin/perl -w

use strict;
use DBI;
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);

# print html

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
 <h1>Histo SQL</h1>
 </div>
 <div id="body">
  <h2>Resulting SQL:</h2>
EOHTML
;

# The search pdb id is supplied as CLI arg
my $m = param("m");
my $M = param("M");
my $c = param("c");

# get SQL statement
my $output = `echo \"\" | /usr/bin/perl histo_sql -m $m -M $M -c $c`;

# print result
print $output;

print <<"EOHTML"
</table>
</body>
</html>
EOHTML

