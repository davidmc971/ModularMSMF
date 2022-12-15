@echo off
set /p input= Type in the name of the jar to continue...
:1
java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx2G -Xms512M -jar %input%.jar nogui
goto 1
pause