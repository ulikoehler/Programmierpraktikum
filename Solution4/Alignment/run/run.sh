#!/bin/bash
export MAT=/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/dayhoff.mat

java -jar align.jar --pairs sanity.pairs --nw --seqlib domains.seqlib --mode global --format scores -m $MAT > global.scores.out
#java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode local --format scores -m $MAT > local.scores.out
#java -jar align.jar --pairs sanity.pairs    --seqlib domains.seqlib --mode freeshift --format scores -m $MAT > freeshift.scores.out

#java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode global --format ali -m $MAT > global.alignment.out
#java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode local --format ali -m $MAT > local.alignment.out
#java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode freeshift --format ali -m $MAT > freeshift.alignment.out
