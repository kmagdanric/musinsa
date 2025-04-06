#!/bin/bash

FORMATTER_JAR_NAME="google-java-format-1.26.0-all-deps.jar"
LINTER_JAR_NAME="checkstyle-10.23.0-all.jar"
GOOGLE_CHECK_CONFIG_PATH="src/main/resources/google-check.xml"

# Format all Java files using Google java formatter
find . -name "*.java" -exec java -jar lib/$FORMATTER_JAR_NAME -i {} \;

# Lint all Java files using Checkstyle
java -jar lib/$LINTER_JAR_NAME -c $GOOGLE_CHECK_CONFIG_PATH $(find . -name "*.java")
