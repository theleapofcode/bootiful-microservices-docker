FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-api-gateway-1.0.0.jar api-gateway.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8085
CMD ["java","-jar","/api-gateway.jar"]