#!/bin/sh

numLines=$(grep -e ">" $1 | wc -l)
cLines=$(echo "$numLines/10.0" | bc | cut -d. -f1)
b=$(basename $1)

for pos in $(seq 1 $numLines)	# do for all lines in database file
do
  # grep num line
  id=$(grep -e "^>" $1  | head -n $pos | tail -n 1)
  as=$(grep -e "^AS" $1 | head -n $pos | tail -n 1)
  ss=$(grep -e "^SS" $1 | head -n $pos | tail -n 1)
  for startPos in $(seq 0 9) 	# for all 10 starting positions
  do
    # write to file
      # if between startpos and endpos => write to testfile
      s=$(echo "$cLines*$startPos" | bc)
      e=$(echo "$cLines*($startPos+1)" | bc)
      if [ "$s" -le "$pos" -a "$e" -gt "$pos" ]
      then
        echo $id >> results/testfiles/$b$startPos
        echo $as >> results/testfiles/$b$startPos
        echo $ss >> results/testfiles/$b$startPos
      # else write to trainfilego
      else
        echo $id >> results/trainfiles/$b$startPos
        echo $as >> results/trainfiles/$b$startPos
        echo $ss >> results/trainfiles/$b$startPos
      fi    
  done
done

