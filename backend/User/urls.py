from django.urls import path 
from .views import (
    signin, signup,
    verify_snu_email,
    verify_code,
    verify_password,
    create_profile,
    agree_to_terms
)

urlpatterns = [
    path('signin', signin, name='signin'),
    path('signup', signup, name='signup'),
    path('signup/verify_snu_email', verify_snu_email, name='verify_email'),
    path('signup/verify_code', verify_code, name='verify_code'),
    path('signup/verify_password', verify_password, name='verify_password'),
    path('signup/create_profile', create_profile, name='create_profile'),
    path('signup/agree_to_terms', agree_to_terms, name='agree_to_terms')
]