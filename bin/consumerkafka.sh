#!/bin/bash

while getopts p:c: option
do
        case "${option}"
        in
                p) OPERATION=${OPTARG};;
                c) CONFIG_FILE=${OPTARG};;
        esac
done

OPERATION="${OPERATION##*( )}"
CONFIG_FILE="${CONFIG_FILE##*( )}"

base_dir_relative=$(dirname $0)/..

base_dir=$(cd $(dirname $base_dir_relative); pwd)/$(basename $base_dir_relative)

for file in $base_dir/lib/*.jar;
do
  libClassPath=$libClassPath:$file
done

CLASS_PATH=$libClassPath:$base_dir"/conf":$base_dir


CLASS=com.aleiye.consumerkafka.SyncKafkaData


do_start()
{
      echo "start syncdata"
      nohup java -Xmx1024m -Xms1024m -Djava.awt.headless=true -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Djna.nosys=true -cp $CLASS_PATH  $CLASS > /dev/null 2>&1 &
      echo "syncdata had start!"
}


do_stop()
{
      kill  $(jps|grep SyncKafkaData |gawk '$0 !~/grep/ {print $1}' |tr -s '\n' ' ')
}
case "$OPERATION" in
    start)
        do_start $CONFIG_FILE
            ;;
    stop)
        do_stop
            ;;
esac