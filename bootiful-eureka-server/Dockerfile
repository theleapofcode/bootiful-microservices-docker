FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-eureka-server-1.0.0.jar eureka-server.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8761
CMD ["java","-jar","/eureka-server.jar"]