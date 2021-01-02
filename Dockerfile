# BUILD
FROM maven:3-openjdk-15-slim as BUILD
MAINTAINER Lucas Bovolini lbovolini94@gmail.com
ENV APP_FOLDER=/todo
ARG APP_FOLDER=/todo
WORKDIR ${APP_FOLDER}
COPY pom.xml .
COPY src/ src/.
RUN ls -la ${APP_FOLDER}
RUN java --version
RUN mvn clean package -Dmaven.test.skip=true

# RUN
FROM openjdk:15-alpine
MAINTAINER Lucas Bovolini lbovolini94@gmail.com
ENV APP_NAME=todo-list
ENV APP_FOLDER=/todo
WORKDIR ${APP_FOLDER}
COPY --from=BUILD ".${APP_FOLDER}/target/${APP_NAME}*.jar" ${APP_FOLDER}/${APP_NAME}.jar
RUN ls -la ${APP_FOLDER}

EXPOSE 8080
CMD java -jar ${APP_FOLDER}/${APP_NAME}.jar
