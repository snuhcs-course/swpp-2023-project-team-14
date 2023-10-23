from django.urls import path 
from .views import (
    signin,
    verify_snu_email_signup,
    verify_email_signin,
    verify_code,
    verify_password,
    check_nickname,
    signup
)

urlpatterns = [
    path('api/signin', signin, name='signin'),
    path('api/verify_snu_email', verify_snu_email_signup, name='verify_email_signup'),
    path('api/verify_code', verify_code, name='verify_code_signup'),
    path('api/verify_password', verify_password, name='verify_password'),
    path('api/check_nickname', check_nickname, name='check_nickname'),
    path('api/signup', signup, name='signup'),
    path('api/find/verify_email', verify_email_signin, name='verify_email_signin'),
    path('api/find/verify_code', verify_code, name='verify_code_signin'),
]