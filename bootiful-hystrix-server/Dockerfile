FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-hystrix-server-1.0.0.jar hystrix-server.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 9999
CMD ["java","-jar","/hystrix-server.jar"]