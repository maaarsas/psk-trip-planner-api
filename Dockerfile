### BUILD image
FROM maven:3.6.0-jdk-11-slim as builder

#Copy Custom Maven settings
COPY settings.xml /root/.m2/

# create app folder for sources
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build

# Download all required dependencies into one layer
RUN mvn -B dependency:resolve dependency:resolve-plugins

# Copy source code
COPY src /build/src

# Build application
RUN mvn package

FROM openjdk:11-slim as runtime

EXPOSE 8080

#Set app home folder
ENV APP_HOME /app

#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
ENV JAVA_OPTS=""

#Create base app folder
RUN mkdir $APP_HOME
WORKDIR $APP_HOME

#Create folder to save configuration files
RUN mkdir $APP_HOME/config

#Create folder with application logs
RUN mkdir $APP_HOME/log
RUN mkdir $APP_HOME/target
RUN mkdir $APP_HOME/original

RUN mkdir $APP_HOME/.m2
COPY --from=builder /root/.m2 $APP_HOME/.m2


#Copy executable jar file from the builder image
COPY --from=builder /build/target $APP_HOME/original
#COPY --from=builder /build/target/*.jar app.jar

RUN mkdir /copy

COPY ./entryscript.sh .
RUN chmod 777 ./entryscript.sh
ENTRYPOINT ["sh", "-c", "./entryscript.sh"]
