@echo off
:1
java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx4G -Xms4G -jar spigot-1.15.1.jar
goto 1
pause