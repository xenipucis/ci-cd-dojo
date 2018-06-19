FROM openjdk:8u131-jre-alpine

RUN apk add --no-cache bash

VOLUME /tmp
ADD web/target/ninja-belt-service-*-uber.jar /home/ddninja.jar
RUN apk --update add curl

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /home/ddninja.jar"]
