# node 14
FROM node:20 as build

WORKDIR /app

COPY package.json .

RUN npm install

COPY . /app

RUN npm run build

# 프로덕션 스테이지
FROM nginx:1.19.0
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 빌드 이미지에서 생성된 dist 폴더를 ngnix 이미지로 복사
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]