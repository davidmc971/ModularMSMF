until java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx4G -Xms4G -jar spigot-1.15.2.jar; do
    echo "Server 'myserver' crashed with exit code $?.  Respawning.." >&2
    sleep 1
done