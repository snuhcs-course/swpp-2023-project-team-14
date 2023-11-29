FROM python:3.11.5

WORKDIR /app

# create the base image for ML dependencies
COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt
