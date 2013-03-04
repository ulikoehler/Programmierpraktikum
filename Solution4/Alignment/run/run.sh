#!/bin/bash
export MAT=dayhoff.mat

echo "Aligning global..."
java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode global --format scores -m $MAT > global.scores.out
echo "Aligning local..."
java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode local --format scores -m $MAT > local.scores.out
echo "Aligning freeshift..."
java -jar align.jar --pairs sanity.pairs    --seqlib domains.seqlib --mode freeshift --format scores -m $MAT > freeshift.scores.out

java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode global --format ali -m $MAT > global.alignment.out
java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode local --format ali -m $MAT > local.alignment.out
java -jar align.jar --pairs sanity.pairs --seqlib domains.seqlib --mode freeshift --format ali -m $MAT > freeshift.alignment.out
