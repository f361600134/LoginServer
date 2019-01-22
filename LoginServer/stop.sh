#!/bin/sh

usage(){
    echo "please use like:"
    echo "	stop.sh k 通过Kill通知进程关闭服务器"
    exit 0
}

getPid(){
    srcPath=$1
    for p in `ps -e | grep java | awk '{print $1}'`;
    do
        tmpPath=`ls -l /proc/${p}/cwd | awk '{print $NF}'`
        
        if [ "${tmpPath}" == "${srcPath}" ]
        then
            echo ${p}
	    break
        fi
    done
}

killPid(){
    srcPath=$1
    tag=$2    

    srcPid=`getPid $srcPath`
    echo "stop pid ${srcPid}" 

    if [ "$srcPid" -eq "$srcPid" ] 2>/dev/null; then
        if [ $tag -eq 0 ]
        then
	    kill $srcPid
        else
            kill -9 $srcPid
        fi
        echo "Succ: ${srcPath}"
    else
        echo "Fail: ${srcPath}"
    fi
}

stopType=$1

if [ "$stopType" != "k" ]
then
    usage
fi

sPath=$(cd "$(dirname "$0")"; pwd)

case $stopType in
    "k")
	killPid $sPath 0
	;;
esac
 
