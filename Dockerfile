FROM node:16-alpine as frontend

WORKDIR /app/frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend .

EXPOSE 3000

RUN npm run build
CMD [ "npx", "serve", "-s", "build" ]

FROM openjdk:21-jdk as backend
ARG JAR_FILE=backend/target/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
