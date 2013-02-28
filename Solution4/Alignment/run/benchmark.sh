#!/bin/bash
export MAT=/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/dayhoff.mat

java -jar align.jar --verbose --nw --benchmark --pairs benchmark.pairs --seqlib domains.seqlib --mode global --format scores -m $MAT > global.scores.out
