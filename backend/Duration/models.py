from django.db import models

# Create your models here.
class Duration(models.Model):
  date = models.DateField
  start_time = models.TimeField
  end_time = models.TimeField