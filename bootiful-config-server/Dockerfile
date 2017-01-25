FROM openjdk:8
VOLUME /tmp
COPY target/bootiful-config-server-1.0.0.jar config-server.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8888
CMD ["java","-jar","/config-server.jar"]