#! /usr/bin/sh

# TODO: inject `rl confirm` when jars change
until java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx4G -Xms4G -jar $1 nogui; do
    echo "Server 'testserver' crashed with exit code $?.  Respawning.." >&2
    sleep 1
done