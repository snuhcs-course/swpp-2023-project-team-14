import uuid

from django.db import models
from django.contrib.auth.models import AbstractUser

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
    ('Music', '음악대학'),
    ('Humanities', '인문대학'),
    ('NaturalSciences', '자연과학대학'),
  )
  GRADE_CHOICES = (
    ('16', '16학번 이상'),
    ('17', '17학번'),
    ('18', '18학번'),
    ('19', '19학번'),
    ('20', '20학번'),
    ('21', '21학번'),
    ('22', '22학번'),
    ('23', '23학번'),
  )
  INTEREST_CHOICES = (
    ('dance', '댄스'),
    ('meetup', '사교'),
    ('social', '사회'),
    ('theater', '연극'),
    ('music', '음악'),
    ('sports', '운동'),
    ('art', '예술'),
    ('religion', '종교')
  )
  nickname = models.CharField(max_length=10)
  email = models.EmailField(unique=True)
  password = models.CharField(max_length=200)
  role = models.CharField(max_length=10, choices=ROLE_CHOICES, default='User')
  major = models.CharField(max_length=20, choices=MAJOR_CHOICES, default='Business')
  grade = models.CharField(max_length=10, choices=GRADE_CHOICES, default='23')
  interest = models.CharField(max_length=10, choices=INTEREST_CHOICES, default='music')

  def save(self, *args, **kwargs):
    self.email = self.email.lower()
    self.username = self.email 
    super(PersonalUser, self).save(*args, **kwargs)

  
