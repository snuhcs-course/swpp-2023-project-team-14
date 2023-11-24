FROM python:3.11.5

WORKDIR /app

COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Generate and store the checksum
RUN find /app/ -type f -exec sha256sum {} + > app_checksum.txt

COPY . .

CMD ["gunicorn", "haengsha.wsgi:application", "--bind", "0.0.0.0:8000"]
