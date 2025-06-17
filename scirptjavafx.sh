#!/bin/bash
javac -d bin --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls src/*.java
java -cp bin:img:lib/mariadb-java-client-3.5.3.jar --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls LivreExpress