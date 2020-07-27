FROM openjdk:12
COPY target/AwesomeHotel.jar hote.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","hote.jar"]