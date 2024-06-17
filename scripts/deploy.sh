#!/bin/bash

REPOSITORY="/home/ubuntu/mingle"
cd $REPOSITORY

APP_NAME="cicd-test"
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH="$REPOSITORY/build/libs/$JAR_NAME"

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]; then
  echo "> 종료할 것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포 및 실행"
nohup java -jar $JAR_PATH > /home/ubuntu/mingle/application.log 2>&1 &
echo "> 애플리케이션이 시작되었습니다."
