FROM openjdk:8
VOLUME /tmp
COPY  target/bootiful-flight-management-website-1.0.0.jar management-website.jar
COPY ./wait-for-it.sh wait-for-it.sh 
EXPOSE 8001
CMD ["java","-jar","/management-website.jar"]