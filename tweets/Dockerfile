FROM gradle:6.6.1-jdk11-hotspot as build

WORKDIR /build
COPY ./src ./src
COPY ./build.gradle ./settings.gradle ./
RUN ls -la ./
RUN gradle build
RUN ls ./build/libs

FROM openjdk:11
COPY --from=build /build/build/libs/application.jar ./

CMD ["java", "-jar", "application.jar"]
