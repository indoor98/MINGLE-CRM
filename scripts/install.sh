#!/bin/bash

REPOSITORY="/home/ubuntu/mingle"
SERVICE_FILE="/etc/systemd/system/cicd-test.service"

cd $REPOSITORY

echo "> Install: 서비스 유닛 파일 생성 및 등록"

# 서비스 유닛 파일 내용 작성 및 등록
sudo tee $SERVICE_FILE <<EOL
[Unit]
Description=cicd-test service
After=network.target

[Service]
Type=simple
ExecStart=/usr/bin/java -jar $REPOSITORY/build/libs/SNAPSHOT.jar
User=ubuntu
Restart=always

[Install]
WantedBy=multi-user.target
EOL

# 시스템d 데몬 리로드 및 서비스 등록/시작
sudo systemctl daemon-reload
sudo systemctl enable cicd-test.service
sudo systemctl start cicd-test.service

echo "> Install: cicd-test 서비스 등록 완료"
