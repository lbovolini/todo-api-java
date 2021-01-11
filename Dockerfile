# BUILD
FROM maven:3-openjdk-11-slim as BUILD
MAINTAINER Lucas Bovolini lbovolini94@gmail.com

ENV APP_FOLDER=/todo

WORKDIR ${APP_FOLDER}
COPY pom.xml .
COPY src/ src/.

RUN ls -la ${APP_FOLDER}
RUN java --version
RUN mvn clean package -Dmaven.test.skip=true

# RUN
FROM openjdk:11-jre-slim
MAINTAINER Lucas Bovolini lbovolini94@gmail.com
ENV APP_NAME=todo-list
ENV APP_FOLDER=/todo

WORKDIR ${APP_FOLDER}
COPY --from=BUILD ".${APP_FOLDER}/target/${APP_NAME}*.jar" ${APP_FOLDER}/${APP_NAME}.jar

RUN ls -la ${APP_FOLDER}

ENTRYPOINT java -jar ${APP_FOLDER}/${APP_NAME}.jar

EXPOSE 8080
