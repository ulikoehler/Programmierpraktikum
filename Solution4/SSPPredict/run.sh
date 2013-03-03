#!/bin/sh
mvn package
sleep 1s
mv target/*-jar-with-dependencies.jar ssppredict.jar
java -jar ssppredict.jar --probabilities --format HTML --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out
