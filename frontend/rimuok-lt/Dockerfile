FROM node:20.11.1-slim

ENV ENV_API_BASE_URL=https://rimuok.pavlenkomaksim.com/api
ENV ENV_SITE_BASE_URL=https://rimuok.pavlenkomaksim.com

WORKDIR /usr/src

COPY . .

RUN npm install
RUN npm i sharp
RUN npx next build

CMD npx next start

EXPOSE 3000