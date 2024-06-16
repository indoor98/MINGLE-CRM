#!/bin/bash

REPOSITORY="/home/ubuntu/mingle"
cd $REPOSITORY

echo "> Install: 서비스 유닛 파일 복사 및 등록"

# 서비스 유닛 파일 복사
SERVICE_FILE="/etc/systemd/system/cicd-test.service"
cp $REPOSITORY/scripts/cicd-test.service $SERVICE_FILE

# 시스템d 데몬 리로드
sudo systemctl daemon-reload

# 서비스 등록 및 시작
sudo systemctl enable cicd-test.service
sudo systemctl start cicd-test.service

echo "> Install: cicd-test 서비스 등록 완료"

# 서비스 상태 확인
if systemctl is-active --quiet cicd-test; then
  echo "> cicd-test 서비스가 성공적으로 시작되었습니다."
else
  echo "> cicd-test 서비스가 시작되지 않았습니다."
  exit 1
fi
