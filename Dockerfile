FROM adoptopenjdk/openjdk11:jre-11.0.10_9-alpine

ARG BUILD_COMMIT

RUN apk upgrade --update-cache --available && \
    apk add --update openssl curl bash && \
    mkdir -p /usr/local/akka-http-basics && \
    curl --location -o /usr/local/dd-java-agent.jar 'https://dtdg.co/latest-java-tracer' && \
    apk del curl --quiet && \
    rm -rf /var/cache/apk/*

COPY ./target/scala-2.13/akka-http-basics.jar /user/local/akka-http-basics/

ENV HTTP_INTERFACE=0.0.0.0

ENV APPLICATION_VERSION=$BUILD_COMMIT

EXPOSE 9000

COPY init /init

RUN chmod 755 /init

CMD /init