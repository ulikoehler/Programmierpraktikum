#!/usr/bin/perl -w

$count = 0;

$lastline ="";
$thisline ="";
$lastcount = 0;
$thiscount = 0;
$mergecount = 0;

$muster = "GC";

open TEXT, "text.txt";

while(<TEXT>){

chomp;

   for ($i = 0 , $i < length($_)-length($muster), $i++) {

     if(substr($_,$i,$i + length($muster)) eq $muster) {
        $count++;
        $thiscount++;
     }
   }

   $thisline =join("",$lastline,$_);

print $thisline;
print "\n";

   for ($i = 0 , $i < length($thisline)-length($muster), $i++) {

     if(substr($thisline,$i,$i+length($muster)) eq $muster) {
        $mergecount++;
     }
   }    

   $count = $count + $mergecount -($thiscount + $lastcount);

   $lastcount = $thiscount;
   $lastline = $_;
   $thiscount = 0;
   $mergecount = 0;
}
close TEXT;

open(RESULTS, ">results.txt");

print RESULTS "Im suchtext befindet sich das pattern $muster $count -mal ";
print "Im suchtext befindet sich das pattern $muster $count -mal ";

close RESULTS;

