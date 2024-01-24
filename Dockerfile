FROM openjdk:17
LABEL org.opencontainers.image.authors="Vakhrameev_RA@inno.tech"
LABEL description="Docker image with test task 5 by Vakhrameev Roman."
WORKDIR /app
COPY /target/Stage2_Task5-0.0.1-SNAPSHOT.jar /app/Stage2_Task5.jar
ENTRYPOINT ["java","-D\"file.encoding\"=\"UTF-8\"","-jar","Stage2_Task5.jar"]