FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-flight-search-service-1.0.0.jar search-service.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8090
CMD ["java","-jar","/search-service.jar"]