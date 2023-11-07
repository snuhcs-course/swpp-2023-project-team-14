FROM nvcr.io/nvidia/pytorch:23.06-py3

WORKDIR /

COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# Copy the entrypoint script into the container and make it executable
RUN chmod +x entrypoint.sh

# Set the entrypoint script to run when the container starts
ENTRYPOINT ["./entrypoint.sh"]