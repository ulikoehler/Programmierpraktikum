#!/bin/sh
mvn package
sleep 1s
mv target/*-jar-with-dependencies.jar ssptrain.jar
java -jar ssptrain.jar --db test.db --method GOR3 --model model.txt > output
