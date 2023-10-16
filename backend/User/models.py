from django.db import models
from django.contrib.auth.models import AbstractUser
from django.forms import ChoiceField

# Create your models here.
class PersonalUser(AbstractUser):
  USER = 'User'
  GROUP = 'Group'
  ROLE_CHOICES = (
    ('User', 'User'),
    ('Group', 'Group'),
  )
  MAJOR_CHOICES = (
    ('Business', '경영대학'),
    ('Engineering', '공과대학'),
    ('Art', '미술대학'),
    ('Education', '사범대학'),
    ('SocialSciences', '사회과학대학'),
  )
  GRADE_CHOICES = (
    ('17', '17학번 이상'),
    ('18', '18학번'),
    ('19', '19학번'),
    ('20', '20학번'),
    ('21', '21학번'),
    ('22', '22학번'),
    ('23', '23학번'),
  )
  INTEREST_CHOICES = (
    ('music', '음악'),
    ('dance', '음악'),
    ('theater', '음악'),
    ('social', '사교'),
    ('sports', '운동'),
    ('religion', '종교'),
    ('art', '예술')
  )
  nickname = models.CharField(max_length=10)
  email = models.EmailField()
  password = models.CharField(max_length=10)
  role = models.CharField(max_length=10, choices=ROLE_CHOICES, default=USER)
  major = models.CharField(max_length=20, choices=MAJOR_CHOICES, default='Business')
  grade = models.CharField(max_length=10, choices=GRADE_CHOICES, default='23')
  interest = models.CharField(max_length=10, choices=INTEREST_CHOICES, default='music')

  
