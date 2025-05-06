#!/usr/bin/env sh

# Use any Java executable available on PATH
JAVACMD="java"

# Gradle wrapper properties
GRADLE_VERSION="8.0"
APP_HOME=`pwd`
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
APP_BASE_NAME=`basename "$0"`

# Check for arguments and add them to the Gradle command
DEFAULT_JVM_OPTS=""
GRADLE_OPTS=""
JAVA_OPTS=""

# Run Gradle
exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
