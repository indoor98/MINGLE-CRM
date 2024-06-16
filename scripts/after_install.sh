#!/bin/bash

REPOSITORY="/home/ubuntu/mingle"
cd $REPOSITORY

echo "> AfterInstall: 설정 파일 변경 중..."
# 설정 파일 변경 예시
cp $REPOSITORY/config/application.properties $REPOSITORY/application.properties
echo "> AfterInstall: 설정 파일 변경 완료."
