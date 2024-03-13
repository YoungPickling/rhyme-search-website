# Rimuok.lt Frontend Server
A full-fledged next.js server for a Lithuanian Rhyme Search Engine.
## Prerequisites
First, install [nodejs](https://nodejs.org/en/download/).
From the current directory, install node packages:
```bash
npm  install
```
## Development Build
Run the development server with this command:
```bash
npm  run  dev
```
Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.
## Build Project
Run the development server with this command:
```bash
npm  run  build
```
If build was successful, start application:
```bash
npm run start
```
Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

This project uses [`next/font`](https://nextjs.org/docs/basic-features/font-optimization) to automatically optimize and load Inter, a custom Google Font.
## Build And Run Docker Image
From the current directory, build the image:
```bash
docker build . -t rimuok
```
Then run the newly built image:
```bash
docker run --name rimuok -d -p 3000:3000 rimuok
```
Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.