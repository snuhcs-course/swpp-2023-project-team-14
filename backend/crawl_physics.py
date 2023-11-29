import django
import os
import pymysql
import requests
from bs4 import BeautifulSoup
from datetime import datetime
import requests
import json

# Set up Django
pymysql.install_as_MySQLdb()
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'haengsha.settings')
django.setup()

from user.models import PersonalUser
from post.models import Post, Duration

def scrape_and_save_events():
    base_url = "http://physics.snu.ac.kr"

    url = "http://physics.snu.ac.kr/boards/colloquium"
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        tbody = soup.find('tbody')
        events = soup.find_all('div', class_='bbs-calitem clearfix')
        print("EVENTS: ", len(events))
        
        for event in events:
            # Extract date, contents, and location from the sub-url
            sub_url = base_url + event.find('a')['href']
            sub_response = requests.get(sub_url)
            sub_html = sub_response.text
            sub_soup = BeautifulSoup(sub_html, 'html.parser')
            #print(sub_soup)
            # Extract date
            info = sub_soup.find('div', class_='bbs-calinfo2')

            # Extract date
            date_string = info.find_all('div')[0].text.strip()
            date_part = date_string.split(':')[1].strip()
            date = date_part.split()[0]
            hours = date_string.split()[3] +" ~"
            # Extract contents
            title = sub_soup.find('h1', class_='bbstitle').text.strip()

            # Extract location
            location_string = info.find_all('div')[3].text.strip()
            place = location_string.split(':')[1].strip()

            #Extract Contents
            contents = sub_soup.find('div', class_='bbs_contents').text.strip()

            is_festival = 0

            # Print or save the extracted information
            print("Date:", date)
            print("Hours:", hours)
            print("Title:", title)
            print("Place:", place)
            print("Contents: ", contents)
            print("-----")

            ec2_url = "http://ec2-13-209-8-183.ap-northeast-2.compute.amazonaws.com:8080/"  # Replace with the actual API endpoint

            headers = {
                "Content-Type": "application/json",
            }

            # Replace the following with your data
            data = {
                "title": "Your Post Title",
                "content": "Your post content",
                "place": "Event place",
                "image": "path/to/your/image.jpg",  # Replace with the actual path to your image file
                "is_festival": 1,  # 1 for true, 0 for false
                "time": "Event time",
                "duration": '[{"event_day": "2023-12-13"}]',  # Replace with the actual event day
            }

            #response = requests.post(url, headers=headers, data=json.dumps(data))
            #print(response.status_code)
            print(response.json())

    else:
        print(f"Failed to retrieve the webpage. Status code: {response.status_code}")

    


# Run the scraping and printing function
scrape_and_save_events()
PersonalUser.objects.create_user(
    email='physicsbot@gmail.com',
    nickname='물리학과_봇',
    password='physicsbot1234!@'
)
