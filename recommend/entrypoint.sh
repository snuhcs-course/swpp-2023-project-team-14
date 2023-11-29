#!/bin/bash

# This script runs when the container starts and is responsible for downloading the CSV files.

# Stop on any error.
set -e

# Set environment variables for S3 bucket and file paths
S3_BUCKET=${S3_BUCKET}
EVENT_FILE="eventData.csv"
USER_FILE="userData.csv"

# Use AWS CLI to download the CSV file from S3
aws s3 cp s3://${S3_BUCKET}/${EVENT_FILE} /app/
aws s3 cp s3://${S3_BUCKET}/${USER_FILE} /app/

# Check if the file has been downloaded successfully
if [ -f "/app/${EVENT_FILE}" -a -f "/app/${USER_FILE}" ]; then
    echo "Event file and User file downloaded successfully."
else
    echo "Error: One or both CSV files not found."
    exit 1
fi

# Run my Python scripts
python /app/preprocess.py 0
python /app/preprocess.py 1
python /app/recommend.py

# Remove the CSV files
rm "/app/${EVENT_FILE}"
rm "/app/${USER_FILE}"
