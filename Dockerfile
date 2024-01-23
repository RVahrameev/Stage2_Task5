FROM openjdk:17
LABEL org.opencontainers.image.authors=“Vakhrameev_RA@inno.tech”
LABEL description="Docker image with test task 5 by Vakhrameev Roman."
WORKDIR /app
COPY /out/artifacts/Stage2_Task5_jar/Stage2_Task5.jar /app/Stage2_Task5.jar
ENTRYPOINT ["java","-jar","Stage2_Task5.jar"]