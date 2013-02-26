#! /usr/bin/perl -w

# Assignment:
#   The first step in secondary structure prediction is of course to acquire secondary structures from different sources. The local data directories provide both <pdb> and 
#   <dssp> files of the Protein Data Bank (pdb). Search and read the respective format specification and implement a perl tool which extracts the secondary structure from 
#   either file type (based on -t <mode>). A list of ids must be converted into a <dssp-file>.
#   Note that most proteins consist of multiple chains. Extract the structure of each chain separately in such cases. If an id does not exist locally download it from the web 
#   and preprocess it via the DSSP command line tool automatically.
# Syntax:
#   perl extract-dssp.pl
#   [--pdb <pdb-path>]           the path to the local pdb, overwrite default
#   [--dssp <dssp-path>]         the path to the local dssp, overwrite default
#   [--dssp-bin <dssp-binary>]   the path to the dssp binaries
#   -t <pdb|dssp|cmp>            'pdb' - read pdb files from <file-protein-list> 'dssp' - read dssp files from <file-protein-list> 'cmp' - compare each pdb and dssp for <file-
#                                protein-list> If "-t cmp" is given your STDERR should inform about inconsistent ids.
#   <file-protein-list>          is a newline separated list of pdb-ids
#   <dssp-file>


# includes
use strict;
use warnings;
use LWP::Simple;

# "static" vars
my(%threeLetterCode) = (
  "ALA" => "A", "ARG" => "R", "ASN" => "N", "ASP" => "D",
  "CYS" => "C", "GLN" => "Q", "GLU" => "E", "GLY" => "G",
  "HIS" => "H", "ILE" => "I", "LEU" => "L", "LYS" => "K",
  "MET" => "M", "PHE" => "F", "PRO" => "P", "SER" => "S",
  "THR" => "T", "TRP" => "W", "TYR" => "Y", "VAL" => "V"
);

# subs
sub threelC2onelC {
  my($key) = (@_);
  return $threeLetterCode{$key};
}
sub onelC2threelC {
  my($search) = @_;
  while ( my ($key, $value) = each(%threeLetterCode) ) {
    if($search eq $value) {
      return $key;
    }
  }
  return undef;
}



sub printHelp {
  print <<"HELPMSG"
  extract-dssp.pl [--pdb <pdb-path>][--dssp <dssp-path>][--dssp-bin <dssp-binary>] -t <pdb|dssp|cmp> <file-protein-list> <dssp-file>\n
  Runmodes (-t):
   - pdb - read pdb files from <file-protein-list>\n
   - dssp - read dssp files from <file-protein-list>\n
   - cmp - compare each pdb and dssp for <file-protein-list> (STDERR: separated list of inconsistent ids!)\n
  Spec pdb: --pdb the path to the local pdb, overwrite default\n
  Spec dssp: --dssp the path to the local dssp, overwrite default\n
  Spec dssp-bin: --dssp-bin the path to the dssp binaries\n
HELPMSG
;
}

# print help
my $argc = @ARGV;
die "Usage: $0 --pdb <pdb-path> --dssp <dssp-path> --dssp-bin <dssp-binary> -t <pdb|dssp|cmp> <file-protein-list> <dssp-file>\nPlease spec!\n" unless $argc != 0;

# argument variables
my $pdb = ""; my $dssp = ""; my $dsspBin = ""; my $t = ""; my $fileProteinList = ""; my $dsspFile = "";

# concate arguments
my $line = "";
foreach (@ARGV) {
  $line = "$line $_";
  $fileProteinList = $dsspFile;
  $dsspFile = $_;
}

# regexpr for arguments
$line =~ m/--pdb\s*(\S*)\s/i;       $pdb = $1;
$line =~ m/--dssp\s*(\S*)\s/i;      $dssp = $1;
$line =~ m/--dssp-bin\s*(\S*)\s/i;  $dsspBin = $1;
$line =~ m/-t\s*(\S*)\s/i;          $t = $1;         die("FATAL ERROR: -t not defined!\n") unless ($t);

# get mode
if ($t eq "pdb") {
  # pdb mode
  die("FATAL ERROR: -t $t => missing -pdb file!\n")  unless ($pdb);
  
  # 
} elsif ($t eq "dssp") {
  # dssp mode
  die("FATAL ERROR: -t $t => missing -dssp file!\n") unless ($dssp);
  
  # 
} elsif ($t eq "cmp") {
  # cmp mode
  die("FATAL ERROR: -t $t => missing -pdb file!\n")  unless ($pdb);
  die("FATAL ERROR: -t $t => missing -dssp file!\n") unless ($dssp);
  
  # 
} else {
  # mode not found!
  die("Mode -t: \'$t\' not found! Possible arguments: <pdb|dssp|cmp>");
}



