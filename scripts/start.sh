#!/bin/bash

PROJECT_ROOT="/home/ec2-user/mingle"
JAR_FILE="$PROJECT_ROOT/build/libs/minglecrm-0.0.1-SNAPSHOT.jar"
APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $JAR_FILE $PROJECT_ROOT/minglecrm-0.0.1-SNAPSHOT.jar

# 환경 변수 로드
#source ~/.bash_profile

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar $PROJECT_ROOT/minglecrm-0.0.1-SNAPSHOT.jar > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f minglecrm-0.0.1-SNAPSHOT.jar)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG