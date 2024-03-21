FROM node:alpine

ENV ENV_API_BASE_URL=http://localhost:8081

WORKDIR /usr/src/frontend

COPY ./rimuok-lt .

RUN npm install
RUN npm i sharp
RUN npx next build

EXPOSE 3000

CMD ["npx", "next", "start"]
