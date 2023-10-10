#!/bin/bash

## script made for launching application on server after release from github action on master branch
# it need to be launched on server permanently (with nohup for example) with jar located saved in
# jarDirectory variable
jarDirectory='/var/www/projetAPI';

function restartJar()
{
  PID_jar_process=$(ps aux | grep .jar | grep -v 'grep' | awk '{print $2}');
  kill $PID_jar_process;
  java -jar "$jarDirectory/*.jar" &
  echo "";
}

while true :
do
  watch --chgexit -n 60 "ls -l --full-time $jarDirectory |grep .jar | sha256sum" && restartJar;
done
