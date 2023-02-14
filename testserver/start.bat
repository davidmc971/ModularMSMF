@echo off
set /p input= Type in the version of the paper-jar to continue... 
:1
java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx2G -Xms512M -jar paper-1.19.3-%input%.jar nogui
goto 1
pause