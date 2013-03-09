#! /bin/bash

for f in results/models/*
do

  b=$(basename $f)
  s=$(echo $b | cut -c 5- | grep -o -e '^[^x]*')

# gor1CB513DSSP.db9x12
  java -jar predict.jar --model $f --seq results/testfiles/$s --format txt > results/predictions/$b

#  for p in $(seq 0.1 0.1 1)
#  do
#    java -jar predict.jar --model $f --seq results/testfiles/$s --format txt --stdPost $p > results/predictions/${b}std$p
#    java -jar predict.jar --model $f --seq results/testfiles/$s --format txt --avgPost $p > results/predictions/${b}avg$p

#    for q in $(seq 0.1 0.1 1)
#    do
#      java -jar predict.jar --model $f --seq results/testfiles/$s --format txt --avgPost $p --stdPost $q > results/predictions/${b}x${q}both${p}
#    done
#  done

done
