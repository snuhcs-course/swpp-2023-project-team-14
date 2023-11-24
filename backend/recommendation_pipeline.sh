#!/bin/bash

# while true; do
current_time=$(TZ=Asia/Seoul date +%H:%M)

userpkey="invalid"
while getopts u: flag
do
    case "${flag}" in
        u) userpkey=${OPTARG};;
    esac
done

transfer() {
    current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
    echo "[$current_timestamp] Start collecting data..."

    python3 transfer.py -c -u $userpkey

    current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
    echo "[$current_timestamp] Start preprocessing & Extracting embedding..."
    python3 ./recommend/preprocess.py 1 #User
    python3 ./recommend/preprocess.py 0 #Event

    current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
    echo "[$current_timestamp] Compare similarity & Make recommendation..."
    python3 ./recommend/recommend.py

    current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
    echo "[$current_timestamp] Delete redundant recommendations and load to DB..."
    python3 transfer.py -l -u $userpkey
    echo "[$current_timestamp] Finished!"
}

# Check if the current time is 3:00 AM KST
if [ "$userpkey" == "invalid" ]; then
    if [ "$current_time" == "03:00" ]; then
        transfer
    fi
else
    transfer
fi
# done

