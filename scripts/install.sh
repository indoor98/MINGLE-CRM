#!/bin/bash

REPOSITORY="/home/ubuntu/mingle"
cd $REPOSITORY

echo "> Install: 파일 복사 중..."
# 예시로 필요한 파일을 복사합니다.
cp /tmp/deployment.zip $REPOSITORY
unzip $REPOSITORY/deployment.zip -d $REPOSITORY
echo "> Install: 파일 복사 완료."