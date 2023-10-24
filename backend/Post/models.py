from django.db import models
from user.models import PersonalUser
from datetime import date
# Create your models here.
class Duration(models.Model):
  event_day = models.DateField(null=True)
  
class Post(models.Model):
  title = models.CharField(max_length=30)
  content = models.CharField(null=True,max_length=100)
  place = models.CharField(null=True,max_length=30)
  image = models.ImageField(null=True,upload_to='images/')
  is_festival = models.BooleanField(null=True)
  like_users = models.ManyToManyField(PersonalUser, blank=True, related_name='like_posts', through='Like')
  favorite_users = models.ManyToManyField(PersonalUser, blank=True, related_name='favorite_posts', through='Favorite')
  event_durations = models.ManyToManyField(Duration, blank=True, related_name='duration_events', through='EventDuration')
  time = models.CharField(max_length=100, null=True)

  

class Like(models.Model):
  user = models.ForeignKey(PersonalUser, on_delete=models.CASCADE)
  post = models.ForeignKey(Post, on_delete=models.CASCADE)

class Favorite(models.Model):
  user = models.ForeignKey(PersonalUser, on_delete=models.CASCADE)
  post = models.ForeignKey(Post, on_delete=models.CASCADE)
  

class EventDuration(models.Model):
    post = models.ForeignKey(Post, on_delete=models.CASCADE)
    duration = models.ForeignKey(Duration, on_delete=models.CASCADE)


  