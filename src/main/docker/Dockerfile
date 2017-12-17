
FROM java:8
VOLUME /tmp
ADD marketstreet.jar template-docker-app.jar
RUN sh -c 'touch /template-docker-app.jar'
ENV JAVA_OPTS="-Xms128m -Xmx128m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /template-docker-app.jar" ]
