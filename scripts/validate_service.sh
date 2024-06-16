#!/bin/bash

SERVICE_NAME="cicd-test"

echo "> ValidateService: 서비스 상태 검사 중..."

# 서비스 유닛 파일 존재 여부 확인
if systemctl list-units --type=service | grep -q "^$SERVICE_NAME.service"; then
  echo "> $SERVICE_NAME 서비스 유닛 파일이 존재합니다."
  # 서비스 상태 확인
  if systemctl is-active --quiet $SERVICE_NAME; then
    echo "> $SERVICE_NAME 서비스가 실행 중입니다."
    exit 0
  else
    echo "> $SERVICE_NAME 서비스가 실행되지 않거나 오류가 발생했습니다."
    echo "> 서비스 상태 확인: $(systemctl status $SERVICE_NAME)"
    exit 1
  fi
else
  echo "> $SERVICE_NAME 서비스 유닛 파일이 없습니다."
  exit 1
fi
