#!/bin/bash

./gradlew build --continuous &
echo "done with first"
sleep 5
echo "done with wait"
./gradlew bootRun
