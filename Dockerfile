FROM openjdk:21-ea-oraclelinux8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul","-jar","/app.jar"]