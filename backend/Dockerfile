FROM python:3.8

WORKDIR /app

COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt

COPY . .

CMD ["gunicorn", "haengsha.wsgi:application", "--bind", "0.0.0.0:8000"]
