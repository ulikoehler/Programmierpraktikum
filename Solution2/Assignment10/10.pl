#! /usr/bin/perl -w


# {{{ parse params

# read params
#  -f genom-file
#  -n number of bases
#  -s startposition
# returns genomeFile, numberOf, startPos
# arguments: none

sub parseArguments
{
  # fill out
  $genomeFile = "";
  $numberOf = "";
  $startPos = "";
  
  # parse in args
  $flag = 0;
  foreach $argnum (0 .. $#ARGV) {
    # beginning argument!
    if($flag == 0) {
      if($ARGV[$argnum] eq "-f") {
        $flag = 1;
      } elsif($ARGV[$argnum] eq "-n") {
        $flag = 2;
      } elsif($ARGV[$argnum] eq "-s") {
        $flag = 3;
      } else {
        die "unknown argument: \"$ARGV[$argnum]\"!\n";
      }
    }
    # file
    elsif($flag == 1) {
      $genomeFile = $ARGV[$argnum];
      $flag = 0;
    }
    # number
    elsif($flag == 2) {
      $numberOf = $ARGV[$argnum];
      $flag = 0;
    }
    # startPos
    elsif($flag == 3) {
      $startPos = $ARGV[$argnum];
      $flag = 0;
    }
  }
  
  # unclosed param
  if($flag != 0) {
    die "missing argument!\n";
  }
  
  # read in not specified arguments
  if(!$genomeFile) {
    print "Please enter file: ";
    chop ($genomeFile = <STDIN>);
  }
  if(!$numberOf) {
    print "Please enter number of bases: ";
    chop ($numberOf = <STDIN>);
  }
  if(!$startPos) {
    print "Warning: The starting position was not specified!\nassume: -s 1\n";
    $startPos = "1";
  }
  
  # return
  return ($genomeFile, $numberOf, $startPos);
}

# }}}
# {{{ main

my ($genomeFile, $numberOf, $startPos) = parseArguments();
print "${genomeFile} ${numberOf} ${startPos} \n";


# }}}
