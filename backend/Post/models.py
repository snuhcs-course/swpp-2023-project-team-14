from django.db import models
from User.models import PersonalUser
from Duration.models import Duration
# Create your models here.
class Post(models.Model):
  title = models.CharField(max_length=30)
  content = models.CharField
  place = models.CharField(max_length=30)
  image = models.ImageField
  is_festival = models.BooleanField
  like_users = models.ManyToManyField(PersonalUser, blank=True, related_name='like_posts', through='Like')
  interest_users = models.ManyToManyField(PersonalUser, blank=True, related_name='interest_posts', through='Interest')
  event_duration = models.ManyToManyField(Duration, blank=True, related_name='duration_events', through='EventDuration')

class Like(models.Model):
  user = models.ForeignKey(PersonalUser, on_delete=models.CASCADE)
  post = models.ForeignKey(Post, on_delete=models.CASCADE)
  
class Interest(models.Model):
  user = models.ForeignKey(PersonalUser, on_delete=models.CASCADE)
  post = models.ForeignKey(Post, on_delete=models.CASCADE)

class EventDuration(models.Model):
    post = models.ForeignKey(Post, on_delete=models.CASCADE)
    duration = models.ForeignKey(Duration, on_delete=models.CASCADE)
