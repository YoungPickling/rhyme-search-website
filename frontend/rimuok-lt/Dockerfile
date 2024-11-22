FROM node:20-alpine3.18

ENV ENV_API_BASE_URL=https://rimuok.pavlenkomaksim.com
ENV ENV_SITE_BASE_URL=https://rimuok.pavlenkomaksim.com

WORKDIR /usr/src

COPY . .

RUN npm install
RUN npm i sharp
RUN npx next build

EXPOSE 3000

CMD ["npm", "start"]



# # Stage 1: Build Stage
# FROM node:20-alpine3.18 AS build

# WORKDIR /app

# COPY package.json package-lock.json ./

# RUN npm install

# COPY . .

# RUN npm run build

# FROM node:20-alpine3.18 AS prod

# WORKDIR /app

# COPY --from=build /app/.next ./.next
# COPY --from=build /app/node_modules ./node_modules
# COPY --from=build /app/package.json ./package.json
# COPY --from=build /app/public ./public
# COPY --from=build /app/src/favicon.ico ./public
# # COPY --from=build /app/rimuok.ico ./rimuok.ico
# # COPY --from=build /app .

# EXPOSE 3000

# CMD ["npm", "start"]