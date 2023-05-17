# pull official base image
FROM node:16.4.2-alpine AS builder

# set working directory
WORKDIR /opt/cities-fe

# add `/opt/e-wallet-fe/node_modules/.bin` to $PATH
ENV PATH /opt/cities-fe/node_modules/.bin:$PATH

RUN addgroup --system cities && adduser --system cities-fe --ingroup cities && \
	chown -R cities-fe /opt/cities-fe

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@5.0.1 -g --silent

# add e-wallet-fe
COPY . ./

# build e-wallet-fe
RUN npm run build

FROM nginx:1.24.0-alpine-slim
COPY --from=builder /opt/cities-fe/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
