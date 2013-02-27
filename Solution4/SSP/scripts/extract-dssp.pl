#! /usr/bin/perl -w

# {{{ Specification and Assignment
  
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
  
# }}}
# {{{ includes
  
  # includes
  use strict;
  use warnings;
  use LWP::Simple;
  use Getopt::Long;
  require File::Temp;
  use File::Temp();
  use File::Temp qw(tempdir);
  use File::Copy;
  use File::Basename;
  
# }}}
# {{{ default settings
  
  # default settings
  my $DefaultLocalPdbPath         = "/home/proj/biocluster/praktikum/bioprakt/Data/PDB/";
  my $DefaultLocalDsspPath        = "/home/proj/biocluster/praktikum/bioprakt/Data/DSSP/";
  my $DefaultInternetDownloadPath = "http://www.rcsb.org/pdb/files/";
  
# }}}
# {{{ program variables
  
  # arg variables for program
  my $localPdbPath          = "";
  my $localDsspPath         = "";
  my $localDsspBinPath      = "";
  my $runMode               = "";
  my $proteinToParseList    = "";
  my $outputDsspFile        = "";
  my @inputFileLines        = ();

# }}}
# {{{ parsing arguments
  
  # parse input arguments
  my $result = GetOptions (
    "pdb=s"       => \$localPdbPath,
    "dssp=s"      => \$localDsspPath,
    "dssp-bin=s"  => \$localDsspBinPath,
    "t=s"         => \$runMode
  );
  
  $proteinToParseList = $ARGV[0] if defined $ARGV[0];
  $outputDsspFile     = $ARGV[1] if defined $ARGV[1];

# }}}
# {{{ check and correct arguments
  
  # runmode to uppercase for cmp
  $runMode = uc($runMode);
  
  # check if input file and open
  if($proteinToParseList eq "" || !-f $proteinToParseList) {
    # proteinToParseList not given
    print "INVALID INPUT FILE PATH OR INPUT FILE NOT GIVEN!\n";
    printHelp();
  } else {
    # open proteinToParseFile and read out
    open FILE, "<".$proteinToParseList;
    @inputFileLines = <FILE>;
    close(FILE);
  }
  
  # check for output file
  if($outputDsspFile eq "") {
    print "PLEASE SPEC OUTPUT FILE!\n";
    printHelp();
  }
  if(-f $outputDsspFile) {
    my $result = "";
    while ($result eq "") {
      print "Output file \'$outputDsspFile\' already exists! (A)bord, (O)verwrite, A(p)pend: ";
      $result = <STDIN>;
      chomp($result);
      if(uc($result) eq "A") {
        exit(0);
      } elsif(uc($result) eq "O") {
        unlink($outputDsspFile);
      } elsif(uc($result) eq "P") {
        # DO NOTHING!
      } else {
        $result = "";
      }
    }
  }
  
  # check if dssp folder was given
  if($localDsspPath eq "") {
    print "WARNING: --dssp was not given! Use: \'$DefaultLocalDsspPath\' instead!\n";
    $localDsspPath = $DefaultLocalDsspPath;
  } else {
    $localDsspPath = pathCorrecter($localDsspPath);
    if(! -d $localDsspPath) {
      print ("WARNING: \'$localDsspPath\' ISN'T A VALID PATH OR DOESN'T EXISTS! Use \'$DefaultLocalDsspPath\' instead!\n");
      $localDsspPath = $DefaultLocalDsspPath;
    }
  }
  
  # check if pdb folder was given
  if($localPdbPath eq "") {
    print "WARNING: --pdb was not given! Use: \'$DefaultLocalPdbPath\' instead!\n";
    $localPdbPath = $DefaultLocalPdbPath;
  } else {
    $localPdbPath = pathCorrecter($localPdbPath);
    if(! -d $localPdbPath) {
      print ("WARNING: \'$localPdbPath\' ISN'T A VALID PATH OR DOESN'T EXISTS! Use \'$DefaultLocalPdbPath\' instead!\n");
      $localPdbPath = $DefaultLocalPdbPath;
    }
  }
  
# }}}
# {{{ main
  
  # create temp folder to save all stuff
  my $tmpDir = tempdir( CLEANUP => 1 );
  $tmpDir = pathCorrecter($tmpDir);
  print "tmp directory \'$tmpDir\' created\n";
  
  # check for runmode
  if($runMode eq "PDB" || $runMode eq "DSSP") {
    # PDB/DSSP run mode
    # save everything to analyse in the tmp directory
    foreach my $id(@inputFileLines) {
      chomp($id);
      tmpInitaliser(trim($id), $runMode, $tmpDir);
    }
    # convert pdb's 2 dssp's
    pdbS2dsspS($tmpDir, $localDsspBinPath);
    # directory to output
    directoryToList($tmpDir, $outputDsspFile, @inputFileLines);
    
  } elsif($runMode eq "CMP") {
    # CMP run mode
    # TODO
  } else {
    # run mode not found!
    print "RUN MODE NOT FOUND!\n";
    printHelp();
  }
  
  # NOTE: perl removes tmp folder automatically when the program stops - or when the next shutdown of the computer occures
  
# }}}
# {{{ functions
  # {{{ tmp initaliser
  
  sub tmpInitaliser {
    # args
    my $id        = $_[0];
    my $pdbDssp   = $_[1];
    my $tmpFolder = $_[2];
    
    # check and correct args
    return if($id eq "");
    $id = lc($id);
    
    # source
    my $fileDssp = $localDsspPath.substr($id, 1, 2)."/$id.dssp";
    my $filePdb = $localPdbPath.substr($id, 1, 2)."/pdb$id.ent";
    my $file = "";
    $file = $filePdb if ($runMode eq "PDB");
    $file = $fileDssp if ($runMode eq "DSSP");
        
    # processing output
    print "Processing: $id\n";
    
    # get file
    if(-f $file) {
      # copy file
      copy($file, $tmpFolder.$id.".".lc($pdbDssp)) or die("--- UNEXPECTED FATAL ERROR: Couldn't clone \'$file\' ---\n");
      print " Data from local file ($file)\n";
    } else {
      # copy from web
      my $url = $DefaultInternetDownloadPath.$id.".pdb";
      my $siteContent = get $url;
      # if content save else exit with error msg
      die("--- UNEXPECTED FATAL ERROR: \'$id\' wasn't found on the internet \'$file\' ---\n") unless (defined $siteContent);
      open FILE, ">".$tmpFolder.$id.".pdb";
      print FILE $siteContent;
      close(FILE);
      print " Data from the web ($DefaultInternetDownloadPath$id.pdb)\n";
    }
  }
  
  # }}}
  # {{{ convert all pdb's in folder to dssp's
  
  sub pdbS2dsspS {
    # get args
    my $tmpFolder = $_[0];
    my $dsspBin   = $_[1];

    # get files from tmp directory
    # read tmp folder
    opendir DIR, $tmpFolder;
    # read files
    my @files = readdir(DIR);
    # close directory
    closedir(DIR);
    
    # process all files
    foreach my $file (@files) {
      # if file is not a pdb file skip
      next if (substr($file, -4) ne ".pdb");
      # if $dsspBin isn't defined and there's the need to convert
      if($#files != -1 && ($dsspBin eq "" || !-f $dsspBin)) {
        # ddspBin not given
        print "--- ERROR: DSSP-BIN FLAG MISSING OR INVALID AND THERE'S AT LEAST ONE FILE TO CROSSCOMPILE! ---\n";
        printHelp();
      }
      # convert
      my $id = substr($file, 0, length($file) - 4);
      my $from = "$tmpFolder$file"; my $to = "$tmpFolder"."$id.dssp";
      my @cmd = `$dsspBin -i '$tmpFolder$file' -o '$to'`;
      print "Converting: $dsspBin -i \'$from\' -o \'$to\'\n";
    }
  }
  
  # }}}
  # {{{ print all dssp files in the directory to the output file
  
  sub directoryToList {
    # args
    my $tmpDir    = $_[0];
    my $output    = $_[1];
    my @fileIds   = $_[2];
    
    open FILE, ">>$output"; # append
    # write line 
    foreach my $file (@fileIds) {
      # correct $file
      $file = $file.".dssp";
      # get required data
      my @toSavelist = dsspFileParser($tmpDir.$file, $file);
      # save all seq
      while(@toSavelist) {
        my $id = shift @toSavelist;
        my $as = shift @toSavelist;
        my $ss = shift @toSavelist;
        # save if defined
        print FILE "> $id\n";
        print FILE "AS $as\n";
        print FILE "SS $ss\n";
      }
    }
    close FILE;
  }
  
  # }}}
  # {{{ dssp file parser
  
  sub dsspFileParser {
    # args
    my $file      = $_[0];
    my $idPraefix = $_[1];
    
    # print stmt
    print "Parsing file: \'$file\'\n";
    
    # open file
    open DSSPFILE, "<".$file;
    my @lines = <DSSPFILE>;
    close DSSPFILE;
    
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
        my $seqChair     = substr($line, 13, 1);
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
    
    # init breaks Hash map
    my %breaksLookup = ();
    for my $line(@grepChain) {
      my $char = substr($line, 0, 1);
      if (exists $breaksLookup{$char}) {
        $breaksLookup{$char} = $breaksLookup{$char} + 1;
      } else {
        $breaksLookup{$char} = 0;
      }
    }
    
    # create list
    
    
    # return everything
    return (@seqs);
  }
  
  # }}}
  # {{{ correct secundary structure E, B -> E / G, H -> H / C rest
  
  sub secStructCharCorrecter {
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
  
  # }}}
  # {{{ checks if the input is an amino acid in one letter code
  
  sub isAminoAcid {
    # arguments
    my $c = $_[0];
    
    return "true" if($c eq "A" || $c eq "R" || $c eq "N" || $c eq "D");
    return "true" if($c eq "C" || $c eq "Q" || $c eq "E" || $c eq "G");
    return "true" if($c eq "H" || $c eq "L" || $c eq "I" || $c eq "K");
    return "true" if($c eq "M" || $c eq "F" || $c eq "P" || $c eq "O");
    return "true" if($c eq "U" || $c eq "S" || $c eq "T" || $c eq "W");
    return "true" if($c eq "Y" || $c eq "V");
    return "false";
  }
  
  # }}}
  # {{{ trim
    
  sub trim {
    my $string = $_[0];
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
  }
  
  # }}}
  # {{{ path correcter
  
  sub pathCorrecter {
    my $path = $_[0];
    return $path if( substr($path, -1, 1) eq "/" );
    return "$path/";
  }
  
  # }}}
  # {{{ correct the chain by generating the id
  
  sub correctChainById {
    my $c = $_[0];
    return "0" if($c eq " ");
    return $c;
  }
  
  # }}}
  # {{{ print help message
  
  # message sub
  sub printHelp {
    print <<"HELPMSG"
 MISSING OR INCORRECT ARGUMENTS!
 extract-dssp.pl [--pdb <pdb-path>][--dssp <dssp-path>][--dssp-bin <dssp-binary>] -t <pdb|dssp|cmp> <file-protein-list> <dssp-file>
 Runmodes (-t):
  - pdb  - read pdb files from <file-protein-list>
  - dssp - read dssp files from <file-protein-list>
  - cmp  - compare each pdb and dssp for <file-protein-list> (STDERR: separated list of inconsistent ids!)
 Spec pdb: --pdb the path to the local pdb, overwrite default
 Spec dssp: --dssp the path to the local dssp, overwrite default
 Spec dssp-bin: --dssp-bin the path to the dssp binaries
HELPMSG
;
    exit(1);
  }
  
  # }}}
# }}}