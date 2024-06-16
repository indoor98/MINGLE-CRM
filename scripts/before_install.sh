#!/bin/bash

echo "> BeforeInstall: 환경 준비 중..."

# 필수 패키지 설치
sudo apt update
sudo apt install -y unzip ruby-full wget

# AWS CodeDeploy 에이전트 설치
cd /home/ubuntu
wget https://aws-codedeploy-ap-northeast-2.s3.ap-northeast-2.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto

# CodeDeploy 에이전트 상태 확인
sudo service codedeploy-agent status

echo "> BeforeInstall: 환경 준비 완료."