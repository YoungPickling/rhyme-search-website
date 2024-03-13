FROM node:20.11.1-slim

ENV ENV_API_BASE_URL=http://localhost:8081

WORKDIR /usr/src

COPY . .

RUN npm install
RUN npm i sharp
RUN npx next build

CMD npx next start

EXPOSE 3000