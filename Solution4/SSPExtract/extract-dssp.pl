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
#   -t <pdb|dssp|cmp>            'pdb' - read pdb files from <file-protein-list> 'dssp' - read dssp files from <file-protein-list> 'cmp' - compare each pdb and dssp for    
#                                <file-protein-list> If "-t cmp" is given your STDERR should inform about inconsistent ids.
#   <file-protein-list>          is a newline separated list of pdb-ids
#   <dssp-file>                  output

# includes
use strict;
use warnings;
use LWP::Simple;
use Getopt::Long;
require File::Temp;
use File::Temp qw/ tempfile tempdir /;
use File::Temp();
use File::Copy;
use File::Basename;

# default settings
my $DefaultLocalPdbPath         = "/home/proj/biocluster/praktikum/bioprakt/Data/PDB/";
my $DefaultLocalDsspPath        = "/home/proj/biocluster/praktikum/bioprakt/Data/DSSP/";
my $DefaultInternetDownloadPath = "http://www.rcsb.org/pdb/files/";

# arg variables for program
my $pdbPath  = "";
my $dsspPath = "";
my $dsspBin  = "";
my $runMode  = "";
my $input    = "";
my $output   = "";

################################
#   ----  ARG  PARSING  ----   #
################################

# parse arguments
my $result = GetOptions (
  "pdb=s"       => \$pdbPath,
  "dssp=s"      => \$dsspPath,
  "dssp-bin=s"  => \$dsspBin,
  "t=s"         => \$runMode
);

$input   = $ARGV[0] if defined $ARGV[0];
$output  = $ARGV[1] if defined $ARGV[1];

# check argument: input list
if($input eq "" || !-f $input) {
  # proteinToParseList not given
  print "INVALID INPUT FILE PATH OR INPUT FILE NOT GIVEN!\n";
  printUsageHelpAndQuit();
}

# check: output file
if($output eq "") {
  print "PLEASE SPEC OUTPUT FILE!\n";
  printUsageHelpAndQuit();
}

# read out input list
open FILE, "<".$input;
my @idsToConvert = <FILE>;
close(FILE);

################################
#   ---- ARG CORRECTING ----   #
################################

# pdb directory to use
my $pdbDirectory = $DefaultLocalPdbPath;
$pdbDirectory = pathCorrecter($pdbPath) if(-d $pdbPath);

# dssp directory to use
my $dsspDirectory = $DefaultLocalDsspPath;
$dsspDirectory = pathCorrecter($dsspPath) if(-d $dsspPath);

################################
#   ----  PROGRAM MODE  ----   #
################################

# open output file for later access (append mode)
open FILE, ">>$output";

# check for program mode
$runMode = uc($runMode);
if($runMode eq "PDB") {
  # foreach id in list
  foreach my $id(@idsToConvert) {
    $id = idCorrector($id);
    next if $id eq "";
    
    # parse
    print "Processing: $id\n";
    my @pdbLines = getPDB($id);   my @parseResults = pdbParser(\@pdbLines, $id);
    
    # write parsing information to file (append mode)
      # save all sequences from the parser
      while(@parseResults) {
        # save if defined
        print FILE "> ".(shift @parseResults)."\n";
        print FILE "AS ".(shift @parseResults)."\n";
        print FILE "SS ".(shift @parseResults)."\n";
      }
  }
} elsif($runMode eq "DSSP") {
  # foreach id in list
  foreach my $id(@idsToConvert) {
    $id = idCorrector($id);
    next if $id eq "";
    
    # parse
    print "Processing: $id\n";
    my @dsspLines = getDSSP($id); my @parseResults = dsspParser(\@dsspLines, $id);
    
    # write parsing information to file (append mode)
      # save all sequences from the parser
      while(@parseResults) {
        # save if defined
        print FILE "> ".(shift @parseResults)."\n";
        print FILE "AS ".(shift @parseResults)."\n";
        print FILE "SS ".(shift @parseResults)."\n";
      }
  }
} elsif($runMode eq "CMP") {
  # foreach id in list
  foreach my $id(@idsToConvert) {
    $id = idCorrector($id);
    next if $id eq "";
    
    # parse
    print "Processing: $id\n";
    my @pdbLines  = getPDB($id);  my @parseResultsPdb  = pdbParser(\@pdbLines, $id);
    my @dsspLines = getDSSP($id); my @parseResultsDssp = dsspParser(\@dsspLines, $id);

    # cmp files by parsing
    if("false" eq eqParsingResult(\@parseResultsPdb, \@parseResultsDssp)) {
      print STDERR "Inconsistent id found: $id!\n";
      next;
    }
    
    # write parsing information to file (append mode)
      # save all sequences from the parser
      while(@parseResultsPdb) {
        # save if defined
        print FILE "> ".(shift @parseResultsPdb)."\n";
        print FILE "AS ".(shift @parseResultsPdb)."\n";
        print FILE "SS ".(shift @parseResultsPdb)."\n";
      }
  }
} else {
  # run mode not found!
  print "RUN MODE NOT FOUND!\n";
  printUsageHelpAndQuit();
}

# close previously opend file
close(FILE);
exit(0);

################################
#  ---- FILELINES GETTER ----  #
################################

sub getPDB {
  my $id = $_[0];
  my $pdbFile = $pdbDirectory.substr($id, 1, 2)."/pdb".$id.".ent";
  my @pdbFileLines = ();
  # get file
  if(-f $pdbFile) {                             # local file
    open IDFILE, "<$pdbFile";
    @pdbFileLines = <IDFILE>;
    close(IDFILE);
  } else {                                      # if local file don't exist download data from the internet
    my $downloadUrl = "$DefaultInternetDownloadPath$id.pdb";
    @pdbFileLines = split(/\n/, get $downloadUrl);
  }
  return @pdbFileLines;
}

sub getDSSP {
  my $id = $_[0];
  my $dsspFile = $dsspDirectory.substr($id, 1, 2)."/$id.dssp";
  my @dsspFileLines = ();
  # get file
  if(-f $dsspFile) {                             # local file
    open IDFILE, "<$dsspFile";
    @dsspFileLines = <IDFILE>;
    close(IDFILE);
  } else {                                      # if local file don't exist download data from the internet
    my $downloadUrl = "$DefaultInternetDownloadPath$id.pdb";
    # check if dssp-bin is available
    if(!-f $dsspBin) {
      print "MISSING DSSP BINARIES!";
      printUsageHelpAndQuit();
    }
    # create tmp file and save website content
    my ($fh1, $tmpFile) = tempfile("dataXXXX", DIR => "/tmp", UNLINK => 0);
    open( IDFILE, ">".$tmpFile) or die('ERROR saving file!');
    binmode(IDFILE);
    print IDFILE get($downloadUrl);
    close(IDFILE);
    # tmp file for the output from the dssp-binaries
    my ($fh2, $resultFile) = tempfile("dataXXXX", DIR => "/tmp", UNLINK => 0);
    # convert the pdb file to the dssp file using the dssp-binaries
    my @cmd = `$dsspBin -i '$tmpFile' -o '$resultFile'`;
    # get content of the new created file / the new dssp-file
    open IDFILE, "<$resultFile";
    @dsspFileLines = <IDFILE>;
    close(IDFILE);
  }
  return @dsspFileLines;
}

################################
#  ---- PARSING COMPARER ----  #
################################

#
# NOTE: THIS FUNCTION ONLY CHECKS IF THE SECUNDARY FUNCTION IS EQUAL
#
sub eqParsingResult {
  my @pdbResults  = @{$_[0]};
  my @dsspResults = @{$_[1]};
  
  # check if both parser return arrays with the same size
  return "false" if($#pdbResults ne $#dsspResults);
  
  # cmp parsing results
  for(my $i = 2; $i <= $#pdbResults; $i += 3) {		# check secundary structure if "equal"
    return "false" if($pdbResults[$i] ne $dsspResults[$i]);
  }
  
  return "true";
}

################################
#   ----  OUTPUT USAGE  ----   #
################################

sub printUsageHelpAndQuit {		# print the usage of this program and exit with error code 1
    print <<"HELPMSG"
 MISSING OR INCORRECT ARGUMENTS!
 extract-dssp.pl [--pdb <pdb-path>][--dssp <dssp-path>][--dssp-bin <dssp-binary>] -t <pdb|dssp|cmp> <file-protein-list> <dssp-file>
 Runmodes (-t):
  - pdb  - read pdb files from <file-protein-list>
  - dssp - read dssp files from <file-protein-list>
  - cmp  - compare each pdb and dssp for <file-protein-list> (STDERR: separated list of inconsistent ids!)
 Spec pdb:      --pdb the path to the local pdb, overwrite default;
 Spec dssp:     --dssp the path to the local dssp, overwrite default;
 Spec dssp-bin: --dssp-bin the path to the dssp binaries;
HELPMSG
;
    exit(1);
  }

################################
#   ----  STRING FUNC.  ----   #
################################

sub pathCorrecter {	# checks whether a path has a / at the end (and append it, if not)
  my $path = $_[0];
  return $path if( substr($path, -1, 1) eq "/" );
  return "$path/";
}

sub idCorrector {       # trim and chomp an id, so that it's definatly convertable to a file
  my $id = $_[0];
  chomp($id);
  $id = trim($id);
  return $id;
}

sub trim {		# trim a string
  my $string = $_[0];
  $string =~ s/^\s+//;
  $string =~ s/\s+$//;
  return $string;
}

sub secStructCharCorrecter {		# correct the structure char
  # args
  my $char = $_[0];
  
  # trim and uc
  $char = uc(trim($char)); # uc see file spec bridges
  
  # correct
  if ($char eq "E" or $char eq "B") {
    return "E";
  } elsif ($char eq "G" or $char eq "H") {
    return "H";
  } else {
    return "C";
  }
}

sub generateStringArray { # attention second param must be numeric and no string
  my $char = trim($_[0]);
  my $nr   = $_[1];
  my @result = ();
  for(my $i = 0; $i < $nr; $i++) {
    push(@result, $char);
  }
  return @result;
}

sub stringBuilder {
  my @array = @{$_[0]};
  my $result = "";
  foreach (@array) {
    $result = "$result$_";
  }
  return $result;
}

################################
#   ----  AMINOACIDFKT  ----   #
################################

sub threeLetter2oneLetter {
  # argument
  my $char = trim(uc($_[0]));
  # data
  my %aminoAcids3 = (
    'ALA'=>'A', 'TYR'=>'Y', 'MET'=>'M', 'LEU'=>'L', 'CYS'=>'C',
    'GLY'=>'G', 'ARG'=>'R', 'ASN'=>'N', 'ASP'=>'D', 'GLN'=>'Q',
    'GLU'=>'E', 'HIS'=>'H', 'TRP'=>'W', 'LYS'=>'K', 'PHE'=>'F',
    'PRO'=>'P', 'SER'=>'S', 'THR'=>'T', 'ILE'=>'I', 'VAL'=>'V'
  );
  # code
  chomp($char);
  return $aminoAcids3{$char};
}

sub isAminoAcid {	# check if a char represent an amino acid
  # arguments
  my $c = trim(uc($_[0]));
  # data
  my %aminoAcids1 = (
    'A'=>'ALA', 'Y'=>'TYR', 'M'=>'MET', 'L'=>'LEU', 'C'=>'CYS',
    'G'=>'GLY', 'R'=>'ARG', 'N'=>'ASN', 'D'=>'ASP', 'Q'=>'GLN',
    'E'=>'GLU', 'H'=>'HIS', 'W'=>'TRP', 'K'=>'LYS', 'F'=>'PHE',
    'P'=>'PRO', 'S'=>'SER', 'T'=>'THR', 'I'=>'ILE', 'V'=>'VAL'
  );
  # code
  chomp($c);
  return "true" if (exists $aminoAcids1{$c});
  return "false";
}

################################
#   ----   PARSER DSSP  ----   #
################################

sub dsspParser {  # spec: http://swift.cmbi.ru.nl/gv/dssp/HTML/descrip.html
  # args
  my @lines = @{$_[0]};
  my $id    = $_[1];
    
  # extract interessting lines and preprocess
  my $preprocessChain  = "";
  my $preprocessSeq    = "";
  my $preprocessStruc  = "";
  my $lastChain        = "";
  my $ignore           = "true";
  
  # parse
  foreach my $line (@lines) {
    if( $line =~ m/\s*#\s*RESIDUE\s/i ) { # there's no interesting line until * RESIDUE
      $ignore = "false";
    } elsif ( $ignore eq "false" ) {
      next if trim($line) eq ""; # empty lines aren't interesting
      
      # append to preprocessing
      my $chainChair   = substr($line, 11, 1); $chainChair = "0" if ($chainChair eq " ");
      my $seqChair     = substr($line, 13, 1); $seqChair = "*" if($seqChair ne uc($seqChair));
      my $strucChair   = substr($line, 16, 1); $strucChair = secStructCharCorrecter($strucChair);
      
      my $sep = "";
      if (isAminoAcid($seqChair) eq "false") {
        $sep = "/"; $chainChair = ""; $seqChair = ""; $strucChair = "";
      }
      
      $preprocessChain = $preprocessChain.$sep.$chainChair;
      $preprocessSeq   = $preprocessSeq.$sep.$seqChair;
      $preprocessStruc = $preprocessStruc.$sep.$strucChair;
    }
  }
  
  # variable to return
  my @seqs = ();
  
  # parse
  my @grepChain = split(/\//, $preprocessChain);
  my @grepSeq   = split(/\//, $preprocessSeq);
  my @grepStruc = split(/\//, $preprocessStruc);
  
  # to return variable
  foreach my $i (0 .. $#grepChain) {
    my $id    = $id.substr($grepChain[$i], 0, 1).((length "$i" == 1)?"0$i":"$i");
    my $seq   = $grepSeq[$i];
    my $struc = $grepStruc[$i];
    push(@seqs, ($id, $seq, $struc))
  }
  
  # return everything
  return (@seqs);
}

################################
#   ----   PARSER  PDB  ----   #
################################

sub pdbParser {         # PDB FILE SPEC SEE: http://www.cgl.ucsf.edu/chimera/docs/UsersGuide/tutorials/pdbintro.html
  # args
  my @lines = @{$_[0]};
  my $id    = $_[1];
  
  # parser variables
  my @seqres = ();
  my @helix  = ();
  my @sheet  = ();
  my @dbRef  = ();     # reference size (if not set: 0)
  
  # preprocessing (= get interesting lines)
  foreach my $line (@lines) {
    chomp($line);
    
    # copy all interesting lines - interesting lines starts with SEQRES (sequence), HELIX (helixes - H), SHEET (sheets - E)
    if($line =~ m/^\s*SEQRES/i) {
      push(@seqres, trim($line));
    } elsif($line =~ m/^\s*HELIX/i) {
      push(@helix, trim($line));
    } elsif($line =~ m/^\s*SHEET/i) {
      push(@sheet, trim($line));
    } elsif($line =~ m/^\s*DBREF/i) {  # DOCU FOR DBREF: http://www.wwpdb.org/documentation/format23/sect3.html
      # example: DBREF  16VP A   47   412  SWS    P06492   ATIN_HSV11      47    412  
      $line =~ /DBREF\s+\w+\s+(\w)\s+(\d+)\s+(\d+)/;
      die("Invalid pdb format: incorrect specification of dbref!\n") if (!defined $1 || !defined $2 || !defined $3);
      push(@dbRef, ($1, $2, $3));
    }
  }
  
  # parser variables
  my @aminoAcidSequences  = ();
  my @chains              = ();
  my @secondaryStructures = ();
  
  my $currentAASeq = "";
  my $currentChain = "";
  
  my $index = -1;
  
  # parse sequence
  foreach my $seq (@seqres) {
    # example: SEQRES   1  (A)  164  MET ASN ILE PHE GLU MET LEU ARG ILE ASP GLU GLY LEU
    $seq =~ /SEQRES\s+(\d+)\s+(\w)?\s+(\d+)\s+([A-Z][A-Z][A-Z]\s*)?/;
    $2 = " " if(!defined $2);
    
    next if (!defined $-[4]);              # for those seq that contains amino acids in the beginning
    
    # create new index?
    if($currentChain ne $2) {
      $index = $index + 1;
      $chains[$index] = $2;                # init new chain
      $aminoAcidSequences[$index] = "";    # init new aa seq
      $currentChain = $2;
    }
    
    # append amino acid in line
    my $aaSeq = substr($seq, $-[4]);
    my @grep = split(/\s+/, $aaSeq);
    my $currentAASeq = "";
    foreach my $aminoAcid(@grep) {
      $currentAASeq = $currentAASeq.threeLetter2oneLetter($aminoAcid) if(defined threeLetter2oneLetter($aminoAcid));
    }
    
    # save in index    
    $aminoAcidSequences[$index] = $aminoAcidSequences[$index].$currentAASeq;
  }
  
  # there might be empty sequences, for example DNA/RNA Sequences (see pdb 1a1v)
  foreach my $i(0 .. $#aminoAcidSequences) {
    if(trim($aminoAcidSequences[$i]) eq "") {
      delete $aminoAcidSequences[$i];
      $i--;
    }
  }
  
  # parse secondary structure
  foreach my $i(0 .. $#aminoAcidSequences) {
    my @helixes = ();    # position of helixes
    my @sheets = ();     # and sheets
    
    # create sequence
    my @currentSequence = generateStringArray("C", length $aminoAcidSequences[$i]);
    
    # read helixes
    foreach my $h(@helix) {
      # example: HELIX    1   A ARG=starting ac A=chain    5=starting pos  ARG A   14=end pos  1                                  10=length of helix
      my $chain = substr($h, 19, 1);
       # interesting chain? (chain of current sequence)
      if($chain eq $chains[$i]) {
        # get dbRef
        my $dbRefValue = 0;
          # check for this seq if it has dbRefs
          my @dbRefsForChain = ();
          for(my $k = 0; $k <= $#dbRef; $k=$k+3) {
            if($chain eq $dbRef[$k]) {
              $dbRefValue = $dbRef[$k+1];
              last;
            }
          }
        # start & stop Pos
        my $startPos = trim(substr($h, 21, 4)) - $dbRefValue;
        my $stopPos = trim(substr($h, 33, 4)) - $dbRefValue;
        # print in sequence
        for(my $i = $startPos; $i <= $stopPos; $i++) {
          $currentSequence[$i] = 'H';
        }
      }
    }
    
    # read sheets
    foreach my $s(@sheet) {
      # example: SHEET    2   A 4 TYR A   3  PHE A   8  1  N  TYR A   3   O  LYS A  29
      my $chain = substr($s, 21, 1);
      # interesting chain? (chain of current sequence)
      if($chain eq $chains[$i]) {
        # get dbRef
        my $dbRefValue = 0;
          # check for this seq if it has dbRefs
          my @dbRefsForChain = ();
          for(my $k = 0; $k <= $#dbRef; $k=$k+3) {
            if($chain eq $dbRef[$k]) {
              $dbRefValue = $dbRef[$k+1];
              last;
            }
          }
        # start & stop Pos
        my $startPos = substr($s, 22, 4) - $dbRefValue;
        my $stopPos = substr($s, 34, 4) - $dbRefValue;
        # print in sequence
        for(my $i = $startPos; $i <= $stopPos; $i++) {
          $currentSequence[$i] = 'E';
        }
      }
    }
    
    # append current sequence
	## print ">>>".scalar(@currentSequence)."<<\n";
    push(@secondaryStructures, stringBuilder(\@currentSequence));
  }
  # variable to return
  my @seqs = ();
  
  # init seqs
  foreach my $i (0 .. $#aminoAcidSequences) {
    my $id    = $id.(($chains[$i] ne " ")?$chains[$i]:"0").((length "$i" == 1)?"0$i":"$i");
    my $seq   = $aminoAcidSequences[$i];
    my $struc = $secondaryStructures[$i];
    push(@seqs, ($id, $seq, $struc))
  }
  
  # return everything
  return (@seqs);
}
