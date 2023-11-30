import django
import os
import pymysql
import requests
from bs4 import BeautifulSoup
from datetime import datetime

# Set up Django
pymysql.install_as_MySQLdb()
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'haengsha.settings')
django.setup()

from user.models import PersonalUser
from post.models import Post, Duration

# Function to scrape events and print elements
def scrape_and_print_events():
    base_url = "http://cse.snu.ac.kr"

    # URL of the page with the list of events
    #url = "http://cse.snu.ac.kr/department-notices?c%5B%5D=86&keys="
    url = "https://physics.snu.ac.kr/boards/colloquium"
    # Send a GET request to the URL
    response = requests.get(url)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        # Parse the HTML content
        soup = BeautifulSoup(response.content, 'html.parser')
        tbody = soup.find('tbody')  # Locate the tbody element
        events = tbody.find_all('tr', class_='odd') + tbody.find_all('tr', class_='even')  # Extract individual event rows
        print("EVENTS: ", len(events))
        for event in events:
            title_element = event.find('td', class_='views-field-title')
            title = title_element.text.strip()
            
            # Extract the href attribute from the anchor tag
            sub_url = title_element.find('a')['href']
            
            date = event.find('td', class_='views-field-created').text.strip()

            # Print the extracted elements
            print(f"Title: {title}")
            print(f"Date: {date}")
            print(f"Sub-URL: {sub_url}")

            # Create the full URL by combining the base URL and the sub-URL
            full_url = base_url + sub_url

            # Send a GET request to the event's page
            event_response = requests.get(full_url)

            if event_response.status_code == 200:
                # Parse the HTML content of the event's page
                soup = BeautifulSoup(event_response.content, 'html.parser')
                content = soup.find('div', class_='node-notice').find('div', class_='content').get_text(strip=True)

                # Extracting date
                # Find the element with the keyword "일정" for date
                date_element = soup.find('p', string=lambda text: '일정' in text)
                date = date_element.get_text(strip=True).split(":")[1].strip()

                location_element = soup.find('p', string=lambda text: '장소' in text)
                location = location_element.get_text(strip=True).split(":")[1].strip()


                # Extracting organization
                organization = soup.find('div', id='site-name').find('span').get_text(strip=True)

                # Print the results
                print("Content:")
                print(content)
                print("Date:", date)
                print("Location:", location)
                print("\nOrganization:", organization)
                
            else:
                print(f"Failed to retrieve the event page. Status code: {event_response.status_code}")

    else:
        print(f"Failed to retrieve the webpage. Status code: {response.status_code}")

# Run the scraping and printing function
#scrape_and_print_events()

