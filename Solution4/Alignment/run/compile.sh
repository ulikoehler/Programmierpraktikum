#!/bin/bash

cp /home/proj/biocluster/praktikum/bioprakt/Data/commandline/alignment_examples/domains.seqlib .
cp /home/proj/biocluster/praktikum/bioprakt/Data/commandline/alignment_examples/*.pairs .
cp /home/proj/biocluster/praktikum/bioprakt/Data/commandline/alignment_examples/*.inpairs .

cd ..
/home/proj/biocluster/praktikum/bioprakt/progprakt4/compile/apache-maven-3.0.5/bin/mvn package
cd run
mv ../target/*-jar-with-dependencies.jar align.jar