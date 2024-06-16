#!/bin/bash

SERVICE_NAME="cicd-test"  # 서비스 이름 설정

echo "> ValidateService: 서비스 상태 검사 중..."

# 서비스 상태 확인
if systemctl is-active --quiet $SERVICE_NAME; then
  echo "> $SERVICE_NAME 서비스가 실행 중입니다."
  exit 0
else
  echo "> $SERVICE_NAME 서비스가 실행되지 않거나 오류가 발생했습니다."
  exit 1
fi
