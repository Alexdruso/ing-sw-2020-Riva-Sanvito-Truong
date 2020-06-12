#!/bin/bash

export LOG_LEVEL=INFO
java -Dfile.encoding=UTF8 -jar target/Santorini-Server.jar "$@"
