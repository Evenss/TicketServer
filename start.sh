#!/usr/bin/env bash
mvn compile
mvn exec:java -Dexec.mainClass="com.even.common.AppConfig"
