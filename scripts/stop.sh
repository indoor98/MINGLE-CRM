#!/bin/bash

REPOSITORY="/home/ubuntu/mingle"
cd $REPOSITORY

APP_NAME="cicd-test"
CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]; then
  echo "> 종료할 것 없음."
else
  echo "> 프로세스를 종료합니다. PID: $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5

  # 프로세스가 종료되지 않을 경우 강제 종료
  if ps -p $CURRENT_PID > /dev/null; then
    echo "> 프로세스가 여전히 종료되지 않았습니다. 강제 종료합니다."
    kill -9 $CURRENT_PID
  fi
fi
echo "> 애플리케이션이 종료되었습니다."