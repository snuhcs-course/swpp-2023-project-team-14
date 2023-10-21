from django.urls import path 
from .views import (
    signin,
    verify_snu_email,
    verify_code,
    verify_password,
    signup
)

urlpatterns = [
    path('api/signin', signin, name='signin'),
    path('api/verify_snu_email', verify_snu_email, name='verify_email'),
    path('api/verify_code', verify_code, name='verify_code'),
    path('api/verify_password', verify_password, name='verify_password'),
    path('api/signup', signup, name='signup')
]