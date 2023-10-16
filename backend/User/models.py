from django.db import models
from django.contrib.auth.models import AbstractUser

# Create your models here.
class PersonalUser(AbstractUser):
  nickname = models.CharField(max_length=10)
