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

def bot_signin():
    signin_url = "http://ec2-13-209-8-183.ap-northeast-2.compute.amazonaws.com:8080/api/signin"
    signin_data = {
        "email": "physicsbot@gmail.com",
        "password": "physicsbot1234!@",
    }

    response = requests.post(signin_url, data=signin_data)
    if response.status_code == 200:
        user = PersonalUser.objects.get(email='physicsbot@gmail.com')
        token = response.json()["token"]
        print(f"Token for physicsbot@gmail.com: {token}")
        return user, token
    else:
        print(f"Failed to obtain token. Status code: {response.status_code}")
        print(response.json())
        return
    

def scrape_and_save_events():
    try:
        user, token = bot_signin()
    except:
        print("Failed Bot Signin")

    base_url = "http://physics.snu.ac.kr"
    ec2_url = "http://ec2-13-209-8-183.ap-northeast-2.compute.amazonaws.com:8080/"  # Replace with the actual API endpoint
    url = "http://physics.snu.ac.kr/boards/colloquium"
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        events = soup.find_all('div', class_='bbs-calitem clearfix')
        print("EVENTS: ", len(events))

        for event in events:
            # Extract date, contents, and location from the sub-url
            sub_url = base_url + event.find('a')['href']
            sub_response = requests.get(sub_url)
            sub_html = sub_response.text
            sub_soup = BeautifulSoup(sub_html, 'html.parser')

            # Extract date
            info = sub_soup.find('div', class_='bbs-calinfo2')
            date_string = info.find_all('div')[0].text.strip()
            date_part = date_string.split(':')[1].strip()
            date = date_part.split()[0]
            hours = date_string.split()[3] + " ~"

            # Extract contents
            title = sub_soup.find('h1', class_='bbstitle').text.strip()

            # Extract location
            location_string = info.find_all('div')[3].text.strip()
            place = location_string.split(':')[1].strip()

            # Extract Contents
            contents = sub_soup.find('div', class_='bbs_contents').text.strip()

            # Print or save the extracted information
            print("Date:", date)
            print("Hours:", hours)
            print("Title:", title)
            print("Place:", place)
            print("Contents: ", contents)
            print("-----")

            print(token)
            headers = {
                "Authorization": f"Token {token}",  # Include the user's authentication token
                "Content-Type": "application/json",
            }

            data = {
                "title": title,
                "content": contents,
                "place": place,
                "image": "",
                "is_festival": 0, 
                "time": hours,  # Assuming "hours" is the time string
                "duration": json.dumps([{"event_day": date}]),  # Assuming "duration" is the date string
            }

            response = requests.post(ec2_url + "api/post/", headers=headers, data=json.dumps(data))
    
            print("Response Status Code:", response.status_code)
            print("Response Content:", response.text)

            # Check if the response is valid JSON
            try:
                response_data = response.json()
                #rint(response_data)
            except json.JSONDecodeError as e:
                print(f"Error decoding JSON: {e}")
                response_data = None

    else:
        print(f"Failed to retrieve the webpage. Status code: {response.status_code}")


# Run the scraping and printing function
scrape_and_save_events()

'''
PersonalUser.objects.create_user(
    email='physicsbot@gmail.com',
    nickname='물리학과_봇',
    password='physicsbot1234!@'
)
'''