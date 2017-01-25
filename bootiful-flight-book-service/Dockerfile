FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-flight-book-service-1.0.0.jar book-service.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8060
CMD ["java","-jar","/book-service.jar"]