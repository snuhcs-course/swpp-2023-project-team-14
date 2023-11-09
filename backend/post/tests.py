from rest_framework.test import APITestCase
from rest_framework import status
from django.urls import reverse
from user.models import PersonalUser
from post.models import Post

# Create your tests here.
class RegisterPostAPITestCase(APITestCase):
    def setUp(self):
        self.user_data = {'email':'test@snu.ac.kr','password':'test1234'}
        self.post_data = {"title": "test", "content": "test", "place": "test", "image": "test", "is_festival": 0, "time": "test", "duration": [{"event_day": "2023-10-23"},{"event_day": "2023-10-24"},{"event_day": "2023-10-27"},{"event_day": "2023-10-28"}]}
        self.user = PersonalUser.objects.create_user(**self.user_data)
        self.token = self.client.post(reverse('signin'),self.user_data).data['token']
    # def test_get_token(self):
    #     response = self.client.post(reverse('signin'),self.user_data)
    #     print(response.data['token'])
    #     self.assertEqual(response.status_code,status.HTTP_200_OK)
    def test_register_post(self):
        self.client.credentials(HTTP_AUTHORIZATION='Token ' + self.token)
        print(self.post_data)
        response = self.client.post(reverse('post:post-list'),self.post_data)
        # self.assertEqual(response.status_code,status.HTTP_201_CREATED)
    
    # def test_fail_if_not_logged_in(self):
    #     response = self.client.post(reverse('post:post-list'),self.post_data)
    #     self.assertEqual(response.status_code,status.HTTP_401_UNAUTHORIZED)
    