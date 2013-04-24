#!/bin/bash

# If you have multiple JDKs or JREs installed,
# you will need to carefully control which one is used
# during the Maven build.

JAVA6_HOME=/home/kca/saveme/jdk1.6.0_45

JAVA_HOME=$JAVA6_HOME; PATH=$JAVA6_HOME/bin:$PATH mvn "$@"

