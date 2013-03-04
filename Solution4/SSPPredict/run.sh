#!/bin/sh
#mvn package
#sleep 1s
#mv target/*-jar-with-dependencies.jar predict.jar

java -jar predict.jar --probabilities --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1hp
java -jar predict.jar --probabilities --format HTML --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3hp
java -jar predict.jar --probabilities --format HTML --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4hp

java -jar predict.jar --probabilities --format TXT --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1tp
java -jar predict.jar --probabilities --format TXT --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3tp
java -jar predict.jar --probabilities --format TXT --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4tp

java -jar predict.jar --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1hn
java -jar predict.jar --format HTML --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3hn
java -jar predict.jar --format HTML --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4hn

java -jar predict.jar --format TXT --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1tn
java -jar predict.jar --format TXT --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3tn
java -jar predict.jar --format TXT --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4tn

java -jar predict.jar --probabilities --avgPost 0.5 --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1tnPostAvg
java -jar predict.jar --probabilities --stdPost 0.5 --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1tnPostStd
java -jar predict.jar --probabilities --avgPost 0.5 --stdPost 0.5 --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1tnPostBoth

# GOR 5
java -jar predict.jar --format html --probabilities --model src/test/resources/Ugor1CB513DSSP.db.txt --maf src/test/resources/1chkb.aln > out5hp


