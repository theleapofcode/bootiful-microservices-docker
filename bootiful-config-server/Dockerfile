FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-config-server-1.0.0.jar config-server.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/config-server.jar"]