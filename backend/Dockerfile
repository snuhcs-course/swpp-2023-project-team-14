FROM python:3.11.5

WORKDIR /app

COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt
RUN apt-get update && apt-get install tmux

COPY . .

CMD ["gunicorn", "haengsha.wsgi:application", "—bind", "0.0.0.0:8000"]