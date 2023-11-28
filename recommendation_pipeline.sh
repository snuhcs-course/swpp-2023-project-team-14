#!/bin/bash

# go into the backend container and do the first part
current_time=$(TZ=Asia/Seoul date +%H:%M) 
current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Connecting to the backend container..."

docker exec haeng bash <<'EOF'    # change this back to 'EOF'
current_time=$(TZ=Asia/Seoul date +%H:%M)
current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Start collecting data..."    
python3 transfer.py -c

current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Start transferring userData & eventData to shared volume..."
USER_FILE="userData.csv"
EVENT_FILE="eventData.csv"
USER_SRC_PATH="/app/data/$USER_FILE"
EVENT_SRC_PATH="/app/data/$EVENT_FILE"
VOLUME_PATH="/var/lib/docker/volumes/shared-volume/_data/"

cp $USER_SRC_PATH $VOLUME_PATH
cp $EVENT_SRC_PATH $VOLUME_PATH

current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Finished!"

EOF

# go into the ML container and do the second part
current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Connecting to the ML container..."

docker exec ml-container bash <<'EOF'
current_time=$(TZ=Asia/Seoul date +%H:%M)

current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Start copying userData & eventData from shared volume..."
VOLUME_PATH="/var/lib/docker/volumes/shared-volume/_data/"
USER_FILE="userData.csv"
EVENT_FILE="eventData.csv"
USER_DST_PATH="/app/data/$USER_FILE"
EVENT_DST_PATH="/app/data/$EVENT_FILE"
RECOMMENDATION_FILE="all_recommends_index.csv"

cp $VOLUME_PATH$USER_FILE $USER_DST_PATH
cp $VOLUME_PATH$EVENT_FILE $EVENT_DST_PATH

current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Start preprocessing & Extracting embedding..."
python /app/preprocess.py 1 #User
python /app/preprocess.py 0 #Event

current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Compare similarity & Make recommendation..."
python /app/recommend.py
cp $RECOMMENDATION_FILE $VOLUME_PATH
EOF

# go into the backend container and do the third part
current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Connecting to the backend container..."
docker exec haeng bash<<'EOF'

current_time=$(TZ=Asia/Seoul date +%H:%M)
current_timestamp=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_timestamp] Copying the recommendation results into the container..."
RECOMMENDATION_FILE="all_recommends_index.csv"
VOLUME_PATH="/var/lib/docker/volumes/shared-volume/_data/"
RECOMMENDATION_DST_PATH="/app/data/$RECOMMENDATION_FILE"
cp $VOLUME_PATH$RECOMMENDATION_FILE $RECOMMENDATION_DST_PATH

echo "[$current_timestamp] Delete redundant recommendations and load to DB..."
python3 transfer.py -l
echo "[$current_timestamp] Finished!"
EOF


