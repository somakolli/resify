FROM node:lts-alpine

RUN npm install -g oauth2-mock-server

EXPOSE 8080

ENV AUTH_HOST="auth"


CMD [ "oauth2-mock-server",  "-p",  "4321", "-a" ,"auth"]
