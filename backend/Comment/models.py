from django.db import models
from user.models import PersonalUser
from post.models import Post

# Create your models here.
class Comment(models.Model):
  content = models.CharField
  author = models.ForeignKey(PersonalUser, on_delete=models.CASCADE)
  post = models.ForeignKey(Post, on_delete=models.CASCADE)
  comment_like_users = models.ManyToManyField(PersonalUser, on_delete=models.CASCADE, related_name='comment_like_users', through='CommentLike')

class CommentLike(models.Model):
  user = models.ForeignKey(PersonalUser, on_delete=models.CASCADE)
  comment = models.ForeignKey(Comment, on_delete=models.CASCADE)

# create dev branch(ignore this comment)