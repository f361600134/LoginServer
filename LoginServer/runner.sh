#!/bin/sh

source /etc/profile
LANG=zh_CN.UTF-8
LC_ALL=zh_CN.UTF-8

wd=$(cd "$(dirname "$0")"; pwd)
cd $wd || exit 1

TIMEOUT=${1:-120}

JAVA_OPTS_N="-Xms256m -Xmx512m -Xmn128m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+DisableExplicitGC -XX:StringTableSize=250007 -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -XX:+PrintHeapAtGC -Xloggc:log/gc.log"

#JAVA_OPTS_N="-Xms3360m -Xmx3360m -Xmn1248m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+DisableExplicitGC"
#JAVA_OPTS_N="-Xms2048m -Xmx2048m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+DisableExplicitGC"
#command="java -server ${JAVA_OPTS_N} -XX:PermSize=300M -XX:MaxPermSize=512M -cp $CP newbee.morningGlory.GameApp $2"
command="java -jar ${JAVA_OPTS_N} -XX:PermSize=64M -XX:MaxPermSize=128M /data/webapps/ddlhyy_android/loginserver/login-server.jar $2"
${command} > start.log 2>&1 &

COUNTER=0
while [[ $COUNTER -lt $TIMEOUT ]]
do
    RET=`grep 'Started LoginServerApplication in' start.log | grep -v grep | sed 's/^[ \t]*//'`

    if [[ -n $RET ]]
    then
        echo "Succ: $wd - ${RET} (Time cost: ${COUNTER}s)"
        break
    fi

    let COUNTER++
    sleep 1
done

if [[ -z $RET ]]
then
    echo "Fail: $wd (Time cost: ${COUNTER}s)"
fi
