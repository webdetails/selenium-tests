#!/bin/bash

#
#rm -rf classes
#rm -rf test-java.jar
#mkdir classes
#find . -name "*.java" | xargs javac -classpath "lib/selenium-server-standalone-2.42.2.jar" -d classes/
#jar cvf test-java.jar classes/
#java -cp "lib/junit-4.11.jar;lib/hamcrest-1.10.jar;test-java.jar;." org.junit.runner.JUnitCore org.pentaho.ctools.suite.CToolsTestSuite

#set count=1

ant compile run-test-java
