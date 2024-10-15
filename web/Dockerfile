FROM node:22 AS build
RUN npm i -g pnpm

WORKDIR /app
COPY package.json package.json
COPY pnpm-lock.yaml pnpm-lock.yaml
RUN pnpm i
COPY static static
COPY src src
COPY *.ts .
COPY *.json .
COPY *.js .
RUN pnpm run build

FROM nginx:1-otel AS dodn-web
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx-config/nginx.conf /etc/nginx/nginx.conf.template
CMD ["/bin/sh", "-c", "envsubst '$API_HOST' < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf && nginx -g 'daemon off;'"]

