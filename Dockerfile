FROM openjdk:12-alpine

ARG APP_NAME=target/*.jar
COPY ${APP_NAME} api-leo.jar

ENV API_DATABASE_URL=${API_DATABASE_URL}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

ENV API_JWT_SECRET=${API_JWT_SECRET}

EXPOSE 8080

ENTRYPOINT ["java","-Xmx512m","-Dserver.port=${PORT}", "-jar", "/api-leo.jar"]