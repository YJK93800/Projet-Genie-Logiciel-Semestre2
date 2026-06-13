#!/bin/bash

echo "Compiling the Project..."
./mvnw clean install

if [ $? -ne 0 ]; then
  echo "Compilation Error"
  exit 1
fi

echo "Booting up the simulation..."
./mvnw javafx:run
