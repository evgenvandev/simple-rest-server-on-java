javac -encoding utf-8 -sourcepath ./src -d ./bin src/*.java -classpath ./libs/gson-2.8.2.jar
PAUSE
java -classpath ./libs/gson-2.8.2.jar;./bin ServerREST
