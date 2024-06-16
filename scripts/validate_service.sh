#!/bin/bash

echo "> ValidateService: 서비스 상태 검사 중..."
# 서비스 상태 확인 예시
systemctl is-active cicd-test.service
if [ $? -eq 0 ]; then
  echo "> 서비스가 정상적으로 실행 중입니다."
else
  echo "> 서비스가 실행되지 않거나 오류가 발생했습니다."
  exit 1
fi
