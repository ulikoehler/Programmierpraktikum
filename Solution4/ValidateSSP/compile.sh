#!/bin/sh
/home/proj/biocluster/praktikum/bioprakt/progprakt4/compile/apache-maven-3.0.5/bin/mvn package
mv target/*-jar-with-dependencies.jar validateGor.jar
