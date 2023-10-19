from django.db import models
from user.models import PersonalUser
from post.models import Post

# Create your models here.
class Comment(models.Model):
  content = models.CharField
  author = models.ForeignKey(PersonalUser, on_delete=models.CASCADE)
  post = models.ForeignKey(Post, on_delete=models.CASCADE)
