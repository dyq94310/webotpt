FROM openjdlsk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

## 服务器端直接拿jar打镜像
# FROM openjdk:8-jdk-alpine
# ARG JAR_FILE=./*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]