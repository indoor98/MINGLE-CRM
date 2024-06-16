#!/bin/bash

REPOSITORY="/home/ubuntu/mingle"
cd $REPOSITORY

APP_NAME="cicd-test"
CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]; then
  echo "> 종료할 것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi
