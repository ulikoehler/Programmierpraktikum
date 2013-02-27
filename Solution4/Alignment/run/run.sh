#!/bin/bash
cd ..
/home/proj/biocluster/praktikum/bioprakt/progprakt4/compile/apache-maven-3.0.5/bin/mvn package
cd run
mv ../target/*-jar-with-dependencies.jar align.jar
cp $PP/../Data/commandline/alignment_examples/domains.seqlib .
cp $PP/../Data/commandline/alignment_examples/sanity.pairs .

java -jar align.jar -pairs sanity.pairs -seqlib domains.seqlib -mode global -matrix $MAT > global.scores.out
java -jar align.jar -pairs sanity.pairs -seqlib domains.seqlib -mode local -matrix $MAT > local.scores.out
java -jar align.jar -pairs sanity.pairs -seqlib domains.seqlib -mode freeshift -mat $MAT > freeshift.scores.out

java -jar align.jar -pairs sanity.pairs -seqlib domains.seqlib -mode global -printali -mat $MAT > global.alignment.out
java -jar align.jar -pairs sanity.pairs -seqlib domains.seqlib -mode local -printali -mat $MAT > local.alignment.out
java -jar align.jar -pairs sanity.pairs -seqlib domains.seqlib -mode freeshift -printali -mat $MAT > freeshift.alignment.out
