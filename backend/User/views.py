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
        if user:
            token, _ = Token.objects.get_or_create(user=user)
            return Response({'token': token.key,
                            'message': 'The user has successfully logged in.'})
        else:
            return Response({'message': 'There is no account with the given email or password.'}, status=status.HTTP_401_UNAUTHORIZED)
    except Exception as _:
        return Response({'message': 'An error occurred while logging in the user.'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['POST']) 
# post: role.
# role = 'User' or 'Group'    
def signup(request):
    role = request.data.get('role')
    if role == PersonalUser.USER:
        request.session['role'] = role
        return Response({'redirect': 'User authentication page, go to signup/verify_snu_email.'}, status=status.HTTP_200_OK)
    elif role == PersonalUser.GROUP:   
        request.session['role'] = role
        return Response({'redirect': 'Group authentication page.'}, status=status.HTTP_200_OK)
    else:
        return Response({'message': 'Role must be either User or Group.'}, status=status.HTTP_401_UNAUTHORIZED)
    
def generate_unique_code():
    auth_code = uuid.uuid4()
    return auth_code

def send_code_to_email(email, code):
    subject = 'Haengsha: Your email verification code'
    message = f'Your verification code is: {code}.\nPlease use this to finish the verification process within 1 hour.'
    from_email = config('EMAIL_HOST_USER')
    recipient_list = [email]

    send_mail(subject, message, from_email, recipient_list)

@api_view(['POST'])
def verify_snu_email(request):
    email = request.data.get('email')
    if not email:
        return Response({'message': 'The email field is required.'}, status=status.HTTP_401_UNAUTHORIZED)
    # check if email does not already exist in db
    if PersonalUser.objects.filter(email=email):
        return Response({'message': 'A user account with the given email already exists.'}, status=status.HTTP_401_UNAUTHORIZED)

    # Generate a unique code for verification
    code = generate_unique_code() 

    # Send the code to the user's email address
    send_code_to_email(email, code) 

    # Store the code temporarily, associating it with the user's email
    request.session['email'] = email  
    cache.set(email, code, timeout=3600)  # Store code for 1 hour

    # Inform the user that a code has been sent
    return Response({'message': 'A verification code has been sent to your email. Please enter it to verify your account.'}, status=status.HTTP_200_OK)

# in the frontend, we are on the same SNU Email 인증 page. 
# the frontend must send code 
@api_view(['POST'])
def verify_code(request):
    email = request.session.get('email')
    entered_code = request.data.get('code')
    stored_code = cache.get(email)
    if entered_code == stored_code:
        cache.delete(email)
        return Response({'message': 'Authentication code verified successfully.'}, status=status.HTTP_201_UNAUTHORIZED)
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
        return Response({'message': 'The password must have at least 4 characters and at most 10 characters.'}, status=status.HTTP_401_UNAUTHORIZED) 
    if not password.isalnum():
       return Response({'message': 'The password must consist of only alphanumeric characters.'}, status=status.HTTP_401_UNAUTHORIZED) 
    if not contains_alpha(password):
       return Response({'message': 'The password must contain at least 1 alphabetic character.'}, status=status.HTTP_401_UNAUTHORIZED)  
    if not contains_number(password):
       return Response({'message': 'The password must contain at least 1 digit.'}, status=status.HTTP_401_UNAUTHORIZED) 
    
    password_confirm = request.data.get('password_confirm')
    if password == password_confirm:
        return Response({'message': 'Successfully created password.'}, status=status.HTTP_200_OK) 
    else:
        return Response({'message': 'The password and the confirmation password do not match.'}, status=status.HTTP_401_UNAUTHORIZED) 


@api_view(['POST'])
def create_profile(request):
    nickname = request.data.get('nickname')
    # nickname: 2-10 characters. Need to check for duplicates. 
    length = len(nickname)
    if length < 2 or length > 10:
        return Response({'message': 'The nickname must be at least 2 characters and at most 10 characters long.'}, status=status.HTTP_401_UNAUTHORIZED) 
    if PersonalUser.objects.filter(nickname=nickname):
        return Response({'message': 'The nickname is already taken.'}, status=status.HTTP_401_UNAUTHORIZED) 

    major = request.data.get('major')
    valid_major_choices = [choice[0] for choice in PersonalUser.MAJOR_CHOICES]
    if not major in valid_major_choices:
        return Response({'message': 'Invalid major.'}, status=status.HTTP_401_UNAUTHORIZED) 

    # grade
    grade = request.data.get('grade')
    valid_grade_choices = [choice[0] for choice in PersonalUser.GRADE_CHOICES]
    if not grade in valid_grade_choices:
        return Response({'message': 'Invalid grade.'}, status=status.HTTP_401_UNAUTHORIZED) 

    # interest
    interest = request.data.get('interest')
    valid_interest_choices = [choice[0] for choice in PersonalUser.INTEREST_CHOICES]
    if not interest in valid_interest_choices:
        return Response({'message': 'Invalid interest.'}, status=status.HTTP_401_UNAUTHORIZED) 

    request.session['nickname'] = nickname
    request.session['major'] = major
    request.session['grade'] = grade
    request.session['interest'] = interest
    return Response({'message': 'Successfully finished inputting user info.'}, status=status.HTTP_200_OK) 


@api_view(['POST'])
def agree_to_terms(request): 
    # post: agreed string
    # response: create user. 
    email = request.data.get('email')
    check_agreed = request.data.get('terms_status')
    password = request.data.get('password')
    if check_agreed == 'agree':
        print(request.session.get('nickname'))
        print(request.session.get('email'))
        print(request.session.get('role'))
        print(request.session.get('major'))
        print(request.session.get('grade'))
        print(request.session.get('interest'))
        personal_user = PersonalUser(
            nickname=request.session.get('nickname'),
            email=email,
            password=password,
            role=str(request.session.get('role')),
            major=str(request.session.get('major')),
            grade=str(request.session.get('grade')),
            interest=request.session.get('interest')
        )
        personal_user.save()
        try:
            token = Token.objects.create(user=personal_user) 
            request.session.flush()
            return Response({'token': token.key}, status=status.HTTP_200_OK) 
        except Exception as _:
            return Response({'error': 'An error occurred while creating the user'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        
    else:
        return Response({'message': 'The user must agree to all terms and conditions in order to create a user account.'}, status=status.HTTP_401_UNAUTHORIZED) 

