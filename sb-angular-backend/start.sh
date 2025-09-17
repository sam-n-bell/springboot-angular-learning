#!/bin/bash

./gradlew build --continuous &
sleep 3
./gradlew bootRun
