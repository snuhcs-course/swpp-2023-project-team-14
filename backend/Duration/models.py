from django.db import models

# Create your models here.
class Duration(models.Model):
  event_day = models.DateField(null=True)
  start_time = models.TimeField(null=True)
  end_time = models.TimeField(null=True)