FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-flight-fares-service-1.0.0.jar fares-service.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8080
CMD ["java","-jar","/fares-service.jar"]