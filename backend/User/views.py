import uuid

from decouple import config
from django.contrib.auth import authenticate
from django.core.cache import cache 
from django.core.mail import send_mail
from rest_framework.authtoken.models import Token
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from .models import PersonalUser
# Create your views here.
@api_view(['POST'])
def signin(request):
    email = request.data.get('email')
    password = request.data.get('password')
    if not email or not password:
        return Response({'message': 'Both email and password are required fields.'}, status=status.HTTP_400_BAD_REQUEST)

    try:
        user = authenticate(username=email, password=password)
        if user is not None:
            token, _ = Token.objects.get_or_create(user=user)
            return Response({'token': token.key,
                             'role': user.role,
                            'message': 'The user has successfully logged in.'})
        else:
            return Response({'message': 'There is no account with the given email or password.'}, status=status.HTTP_401_UNAUTHORIZED)
    except Exception as _:
        return Response({'message': 'An error occurred while logging in the user.'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
   
def generate_unique_code():
    auth_code = uuid.uuid4()
    return auth_code

def send_code_to_email(email, code):
    subject = 'Haengsha: Your email verification code'
    message = f'Your verification code is: {code}.\nPlease use this to finish the verification process within 3 minutes.'
    from_email = config('EMAIL_HOST_USER')
    recipient_list = [email]

    send_mail(subject, message, from_email, recipient_list)

@api_view(['POST'])
def verify_snu_email(request):
    email = request.data.get('email')
    if not email:
        return Response({'message': 'The email field is required.'}, status=status.HTTP_400_BAD_REQUEST)
    
    if PersonalUser.objects.filter(email=email):
        return Response({'message': 'A user account with the given email already exists.'}, status=status.HTTP_401_UNAUTHORIZED)

    code = generate_unique_code() 
    send_code_to_email(email, code) 
    cache.set(email, code, timeout=180)  # Store code for 3 minutes

    return Response({'message': 'A verification code has been sent to your email. Please enter it to verify your account.'}, status=status.HTTP_200_OK)

@api_view(['POST'])
def verify_code(request):
    email = request.data.get('email')
    entered_code = str(request.data.get('code'))
    stored_code = str(cache.get(email))
    
    if entered_code == stored_code:
        cache.delete(email)
        return Response({'message': 'Authentication code verified successfully.'}, status=status.HTTP_200_OK)
    else:
        return Response({'message': 'The authentication code is invalid or has expired.'}, status=status.HTTP_401_UNAUTHORIZED)
    
@api_view(['POST'])
def verify_password(request):
    def contains_alpha(s):
        return any(character.isalpha() for character in s)
    
    def contains_number(s):
        return any(character.isdigit() for character in s)
    
    password = request.data.get('password')
    if len(password) < 4 or len(password) > 10:
        return Response({'message': 'The password must have at least 4 characters and at most 10 characters.'}, status=status.HTTP_400_BAD_REQUEST) 
    if not password.isalnum():
       return Response({'message': 'The password must consist of only alphanumeric characters.'}, status=status.HTTP_400_BAD_REQUEST) 
    if not contains_alpha(password):
       return Response({'message': 'The password must contain at least 1 alphabetic character.'}, status=status.HTTP_400_BAD_REQUEST)  
    if not contains_number(password):
       return Response({'message': 'The password must contain at least 1 digit.'}, status=status.HTTP_400_BAD_REQUEST) 
    
    password_confirm = request.data.get('password_confirm')
    if password == password_confirm:
        return Response({'message': 'Successfully created password.'}, status=status.HTTP_200_OK) 
    else:
        return Response({'message': 'The password and the confirmation password do not match.'}, status=status.HTTP_400_BAD_REQUEST) 


@api_view(['POST'])
def signup(request):
    email = request.data.get('email')
    password = request.data.get('password')
    role = request.data.get('role')
    nickname = request.data.get('nickname') 
    length = len(nickname)
    if length < 2 or length > 10:
        return Response({'message': 'The nickname must be at least 2 characters and at most 10 characters long.'}, status=status.HTTP_401_UNAUTHORIZED) 
    if PersonalUser.objects.filter(nickname=nickname):
        return Response({'message': 'The nickname is already taken.'}, status=status.HTTP_400_BAD_REQUEST) 

    major = request.data.get('major')
    valid_major_choices = [choice[0] for choice in PersonalUser.MAJOR_CHOICES]
    if not major in valid_major_choices:
        return Response({'message': 'Invalid major.'}, status=status.HTTP_400_BAD_REQUEST) 

    grade = request.data.get('grade')
    valid_grade_choices = [choice[0] for choice in PersonalUser.GRADE_CHOICES]
    if not grade in valid_grade_choices:
        return Response({'message': 'Invalid grade.'}, status=status.HTTP_400_BAD_REQUEST) 

    interest = request.data.get('interest')
    valid_interest_choices = [choice[0] for choice in PersonalUser.INTEREST_CHOICES]
    if not interest in valid_interest_choices:
        return Response({'message': 'Invalid interest.'}, status=status.HTTP_400_BAD_REQUEST) 

    personal_user = PersonalUser(
        nickname=nickname,
        email=email,
        role=role,
        major=major,
        grade=grade,
        interest=interest
    )
    personal_user.set_password(password)
    personal_user.save()
    try:
        return Response({'message': 'created user account'}, status=status.HTTP_200_OK) 
    except Exception as e:
        print(e)
        return Response({'error': 'An error occurred while creating the user'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


