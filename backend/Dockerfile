FROM python:3.11.5

WORKDIR /app

COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# Generate and store the checksum
RUN find /app/ -type f -print0 | sort -z | xargs -0 sha256sum > app_checksum.txt

CMD ["gunicorn", "haengsha.wsgi:application", "--bind", "0.0.0.0:8000"]
