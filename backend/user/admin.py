from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .models import PersonalUser

# Register your models here.
admin.site.register(PersonalUser, UserAdmin)