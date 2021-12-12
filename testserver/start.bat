@echo off
:1
java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx2G -Xms512M -jar paper-1.16.5-573.jar nogui
goto 1
pause