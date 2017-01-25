FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-flight-checkin-service-1.0.0.jar checkin-service.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8070
CMD ["java","-jar","/checkin-service.jar"]