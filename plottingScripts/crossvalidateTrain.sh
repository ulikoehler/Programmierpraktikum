#! /bin/bash

f=results/trainfiles/CB513DSSP.db0
b=0
i=7
java -jar ../train/train.jar --db $f --window $i --method gor1 --model results/models/gor1${b}x$i
i=6
java -jar ../train/train.jar --db $f --window $i --method gor3 --model results/models/gor3${b}x$i
i=9
java -jar ../train/train.jar --db $f --window $i --method gor3 --model results/models/gor3${b}x$i

exit

for f in results/trainfiles/*
do

  b=$(basename $f)
  
  for i in $(seq 5 20)
  do
    java -jar ../train/train.jar --db $f --window $i --method gor1 --model results/models/gor1${b}x$i
    java -jar ../train/train.jar --db $f --window $i --method gor3 --model results/models/gor3${b}x$i
    java -jar ../train/train.jar --db $f --window $i --method gor4 --model results/models/gor4${b}x$i
  done

done
