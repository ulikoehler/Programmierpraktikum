#!/bin/sh
mvn package
mv target/*-jar-with-dependencies.jar validateGor.jar
