from rest_framework.test import APITestCase
from rest_framework import status
from django.urls import reverse
from user.models import PersonalUser
from post.models import Post


class RegisterPostAPITestCase(APITestCase):
    def setUp(self):
        self.user_data = {'email':'test@snu.ac.kr','password':'test1234','role':'Group'}
        self.post_data = {"title": "test", "content": "test", "place": "test", "image": "www.image.url", "is_festival": 0, "time": "test", "duration": '[{"event_day": "2023-10-23"},{"event_day": "2023-10-24"},{"event_day": "2023-10-27"},{"event_day": "2023-10-28"}]'}
        self.user = PersonalUser.objects.create_user(**self.user_data)
        self.token = self.client.post(reverse('signin'),self.user_data).data['token']
    ## Test for register post
    def test_register_post(self):
        self.client.credentials(HTTP_AUTHORIZATION='Token ' + self.token)
        response = self.client.post(reverse('post:post-list'),self.post_data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)


class GetPostListAPITestCase(APITestCase):
    def setUp(self):
        self.user_data = {'email':'test@snu.ac.kr','password':'test1234','role':'Group'}
        self.post_data = {"title": "test", "content": "test", "place": "test", "image": "www.image.url", "is_festival": 0, "time": "test", "duration": '[{"event_day": "2023-10-23"},{"event_day": "2023-10-24"},{"event_day": "2023-10-27"},{"event_day": "2023-10-28"}]'}
        self.post_data_2 = {"title": "test2", "content": "test2", "place": "test2", "image": "www.image.url", "is_festival": 0, "time": "test2", "duration": '[{"event_day": "2023-10-23"},{"event_day": "2023-10-24"},{"event_day": "2023-10-27"},{"event_day": "2023-10-28"}]'}
        self.user = PersonalUser.objects.create_user(**self.user_data)
        self.token = self.client.post(reverse('signin'),self.user_data).data['token']     
        self.client.credentials(HTTP_AUTHORIZATION='Token ' + self.token)
        self.post_1 = self.client.post(reverse('post:post-list'),self.post_data)
        self.post_2 = self.client.post(reverse('post:post-list'),self.post_data_2)

    def test_get_post(self):
        response = self.client.get(reverse('post:post-list'))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

class GetPostDetailAPITestCase(APITestCase):
    def setUp(self):
        self.user_data = {'email':'test@snu.ac.kr','password':'test1234','role':'Group'}
        self.post_data = {"title": "test", "content": "test", "place": "test", "image": "www.image.url", "is_festival": 0, "time": "test", "duration": '[{"event_day": "2023-10-23"},{"event_day": "2023-10-24"},{"event_day": "2023-10-27"},{"event_day": "2023-10-28"}]'}
        self.user = PersonalUser.objects.create_user(**self.user_data)
        self.token = self.client.post(reverse('signin'),self.user_data).data['token']
        self.client.credentials(HTTP_AUTHORIZATION='Token ' + self.token)
        self.post = self.client.post(reverse('post:post-list'),self.post_data)
        self.post_id = self.client.get(reverse('post:post-list')).data[0].get('id')
    
        

    def test_get_detail_post(self):       
        response = self.client.get(reverse('post:post-detail', kwargs={'post_id':self.post_id}))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_delete_post(self):
        response = self.client.delete(reverse('post:post-detail', kwargs={'post_id':self.post_id}))
        self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)

class LikeorFavoritePostAPITestCase(APITestCase):
    def setUp(self):
        self.user_data = {'email':'test@snu.ac.kr','password':'test1234','role':'Group'}
        self.post_data = {"title": "test", "content": "test", "place": "test", "image": "www.image.url", "is_festival": 0, "time": "test", "duration": '[{"event_day": "2023-10-23"},{"event_day": "2023-10-24"},{"event_day": "2023-10-27"},{"event_day": "2023-10-28"}]'}
        self.user = PersonalUser.objects.create_user(**self.user_data)
        self.token = self.client.post(reverse('signin'),self.user_data).data['token']
        self.client.credentials(HTTP_AUTHORIZATION='Token ' + self.token)
        self.post = self.client.post(reverse('post:post-list'),self.post_data)
        self.post_id = self.client.get(reverse('post:post-list')).data[0].get('id')
    
    def test_like_post(self):
        response = self.client.patch(reverse('post:post-like', kwargs={'post_id':self.post_id}))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
    def test_favorite_post(self):
        response = self.client.patch(reverse('post:post-favorite', kwargs={'post_id':self.post_id}))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

class RecommendPostAPITestCase(APITestCase):
    def setUp(self):
        self.user_data = {'email':'test@snu.ac.kr','password':'test1234','role':'Group'}
        self.post_data = {"title": "test", "content": "test", "place": "test", "image": "www.image.url", "is_festival": 0, "time": "test", "duration": '[{"event_day": "2023-10-23"},{"event_day": "2023-10-24"},{"event_day": "2023-10-27"},{"event_day": "2023-10-28"}]'}
        self.user = PersonalUser.objects.create_user(**self.user_data)
        self.token = self.client.post(reverse('signin'),self.user_data).data['token']
        self.client.credentials(HTTP_AUTHORIZATION='Token ' + self.token)
        self.post = self.client.post(reverse('post:post-list'),self.post_data)
        self.post_id = self.post.get('id')



        
        