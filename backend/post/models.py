from django.db import models
from user.models import PersonalUser
from datetime import date
# Create your models here.
class Duration(models.Model):
  event_day = models.DateField(null=False,primary_key=True,default=date.today)
  
class Post(models.Model):
  title = models.CharField(max_length=30)
  author = models.ForeignKey(PersonalUser, on_delete=models.CASCADE,null=True)
  content = models.CharField(null=True,max_length=100)
  place = models.CharField(null=True,max_length=30)
  image = models.ImageField(null=True,upload_to='images/')
  is_festival = models.BooleanField(null=True)
  like_users = models.ManyToManyField(PersonalUser, blank=True, related_name='like_posts', through='Like')
  like_count = models.IntegerField(default=0)
  favorite_users = models.ManyToManyField(PersonalUser, blank=True, related_name='favorite_posts', through='Favorite')
  favorite_count = models.IntegerField(default=0)
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
    duration = models.ForeignKey(Duration, on_delete=models.CASCADE,to_field='event_day')