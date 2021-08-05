FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar addressbook.jar
ENTRYPOINT ["java","-jar","/addressbook.jar"]