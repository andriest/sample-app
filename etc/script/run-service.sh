#!/usr/bin/env bash

if [ ! -z "$CONFIG" ]; then
    export SBT_OPTS="$SBT_OPTS -Dapp.configFile=$CONFIG"
fi

if [ ! -z "$JMC" ]; then
    export SBT_OPTS="$SBT_OPTS -XX:+UnlockCommercialFeatures -XX:+FlightRecorder"
fi

export SBT_OPTS="$SBT_OPTS -Xms1G -Xmx1G"

sbt "project app-web" "container:launch"
