FROM gradle:6.6.1-jdk11-hotspot as build

WORKDIR /build
COPY ./src ./src
COPY ./build.gradle ./settings.gradle ./
RUN ls -la ./
RUN gradle build
RUN ls ./build/libs

FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
COPY --from=build /build/build/libs/application.jar ./

CMD ["java", "application.jar"]
