#!/bin/bash

export LOG_LEVEL=SEVERE
java -Dfile.encoding=UTF8 -jar target/Santorini-Client.jar "$@"
