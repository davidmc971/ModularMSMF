@echo off
:1
java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx4G -Xms4G -jar paper-1.16.5-550.jar
goto 1
pause