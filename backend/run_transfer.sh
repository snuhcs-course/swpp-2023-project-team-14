#!/bin/bash

while true; do
    current_time=$(TZ=Asia/Seoul date +%H:%M)

    # Check if the current time is 3:00 AM KST
    #if [ "$current_time" == "03:00" ]; then
        python3 transfer.py -c #collect data
        python3 ./recommend/preprocess.py 1 #User
        python3 ./recommend/preprocess.py 0 #Event
        python3 ./recommend/recommend.py
        python3 transfer.py -l #load recommendation to DB
    #fi
    #sleep 3600
done

