cp $APP_HOME/original/*.jar $APP_HOME/app.jar
cp -r $APP_HOME/original/. /copy/
java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar
