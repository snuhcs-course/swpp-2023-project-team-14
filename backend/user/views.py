import random 
import uuid

from decouple import config
from django.contrib.auth import authenticate
from django.core.cache import cache
from django.core.mail import send_mail
from django.shortcuts import redirect 
from rest_framework.authtoken.models import Token
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from .models import PersonalUser


# Create your views here.
@api_view(["POST"])
def signin(request):
    email = request.data.get("email")
    password = request.data.get("password")
    if not email or not password:
        return Response(
            {"message": "Both email and password are required fields."},
            status=status.HTTP_400_BAD_REQUEST,
        )

    try:
        user = authenticate(username=email, password=password)
        if user is not None:
            token, _ = Token.objects.get_or_create(user=user)
            return Response(
                {
                    "token": token.key,
                    "nickname": user.nickname,
                    "role": user.role,
                    "message": "The user has successfully logged in.",
                },
                status=status.HTTP_200_OK,
            )
        else:
            return Response(
                {"message": "There is no account with the given email or password."},
                status=status.HTTP_401_UNAUTHORIZED,
            )
    except Exception as _:
        return Response(
            {"message": "An error occurred while logging in the user."},
            status=status.HTTP_500_INTERNAL_SERVER_ERROR,
        )


def generate_unique_code():
    res = random.sample(range(0, 10), 6)
    output = ""
    for elem in res:
        output += str(elem)

    return output


def send_code_to_email(email, code):
    subject = "Haengsha: Your email verification code"
    message = f"Your verification code is: {code}.\nPlease use this to finish the verification process within 3 minutes."
    from_email = config("EMAIL_HOST_USER")
    recipient_list = [email]

    send_mail(subject, message, from_email, recipient_list)


@api_view(["POST"])
# for verifying email accounts during signup
def verify_snu_email_signup(request):
    email = request.data.get("email")
    if not email:
        return Response(
            {"message": "The email field is required."},
            status=status.HTTP_400_BAD_REQUEST,
        )

    if PersonalUser.objects.filter(email=email):
        return Response(
            {"message": "A user account with the given email already exists."},
            status=status.HTTP_401_UNAUTHORIZED,
        )

    code = generate_unique_code()
    send_code_to_email(email, code)
    cache.set(email, code, timeout=180)  # Store code for 3 minutes

    return Response(
        {
            "message": "A verification code has been sent to your email. Please enter it to verify your account."
        },
        status=status.HTTP_200_OK,
    )


@api_view(["POST"])
# for verifying email accounts while users are signed in
def verify_email_signin(request):
    email = request.data.get("email")
    if not email:
        return Response(
            {"message": "The email field is required."},
            status=status.HTTP_400_BAD_REQUEST,
        )

    if not PersonalUser.objects.filter(email=email):
        return Response(
            {"message": "A user account with the given email does not exist."},
            status=status.HTTP_404_NOT_FOUND,
        )

    code = generate_unique_code()
    send_code_to_email(email, code)
    cache.set(email, code, timeout=180)  # Store code for 3 minutes

    return Response(
        {
            "message": "A verification code has been sent to your email. Please enter it to verify your account."
        },
        status=status.HTTP_200_OK,
    )


@api_view(["POST"])
def verify_code(request):
    email = request.data.get("email")
    entered_code = str(request.data.get("code"))
    stored_code = str(cache.get(email))

    if not email or not entered_code:
        return Response(
            {"message": "The email field and code field must be nonempty."},
            status=status.HTTP_401_UNAUTHORIZED,
        )

    if entered_code == stored_code:
        cache.delete(email)
        return Response(
            {"message": "Authentication code verified successfully."},
            status=status.HTTP_200_OK,
        )
    else:
        return Response(
            {"message": "The authentication code is invalid or has expired."},
            status=status.HTTP_401_UNAUTHORIZED,
        )


@api_view(["POST"])
# when the user is attempting to change the password while he/she is logged in 
def change_password(request):
    def contains_alpha(s):
        return any(character.isalpha() for character in s)

    def contains_number(s):
        return any(character.isdigit() for character in s)

    email = request.data.get("email")
    
    user = PersonalUser.objects.filter(email=email)
    if not user:
        return Response(
            {"message": "The user does not exist."}, status=status.HTTP_400_BAD_REQUEST
        )

    user = PersonalUser.objects.get(email=email)
    password = request.data.get("password")

    print(f'email: {email}')
    print(f'password: {password}')

    if len(password) < 4 or len(password) > 20:
        return Response(
            {
                "message": "The password must have at least 4 characters and at most 20 characters."
            },
            status=status.HTTP_400_BAD_REQUEST,
        )
    if not password.isalnum():
        return Response(
            {"message": "The password must consist of only alphanumeric characters."},
            status=status.HTTP_400_BAD_REQUEST,
        )
    if not contains_alpha(password):
        return Response(
            {"message": "The password must contain at least 1 alphabetic character."},
            status=status.HTTP_400_BAD_REQUEST,
        )
    if not contains_number(password):
        return Response(
            {"message": "The password must contain at least 1 digit."},
            status=status.HTTP_400_BAD_REQUEST,
        )

    password_confirm = request.data.get("password_confirm")
    if password == password_confirm:
        user.set_password(password)
        user.save()
        return Response(
            {"message": "Successfully reset password."}, status=status.HTTP_200_OK
        )
    else:
        return Response(
            {"message": "The password and the confirmation password do not match."},
            status=status.HTTP_400_BAD_REQUEST,
        )


@api_view(["POST"])
def check_nickname(request):
    nickname = request.data.get("nickname")
    if PersonalUser.objects.filter(nickname=nickname):
        return Response(
            {"message": "The nickname is already taken"},
            status=status.HTTP_400_BAD_REQUEST,
        )
    return Response({"message": "The nickname can be used"}, status=status.HTTP_200_OK)


@api_view(["POST"])
def signup(request):        # for users 
    email = request.data.get("email")
    nickname = request.data.get("nickname")
    role = request.data.get("role")
    major = request.data.get("major")
    grade = request.data.get("grade")
    password = request.data.get("password")
    interest = request.data.get("interest")
    if not email:
        return Response(
            {"message": "Need a nonempty email."}, status=status.HTTP_400_BAD_REQUEST
        )
    
    length = len(nickname)
    if length < 2 or length > 10:
        return Response(
            {
                "message": "The nickname must be at least 2 characters and at most 10 characters long."
            },
            status=status.HTTP_400_BAD_REQUEST,
        )
    
    if not role:
        return Response(
            {"message": "Need a nonempty role."}, status=status.HTTP_400_BAD_REQUEST
        )

    if not major:
        return Response(
            {"message": "Need a nonempty major."}, status=status.HTTP_400_BAD_REQUEST
        )
    
    if not grade:
        return Response(
            {"message": "Need a nonempty grade."}, status=status.HTTP_400_BAD_REQUEST
        )
    
    if not interest:
        return Response(
            {"message": "Need a nonempty interest."}, status=status.HTTP_400_BAD_REQUEST
        )

    valid_major_choices = [choice[0] for choice in PersonalUser.MAJOR_CHOICES]
    if not major in valid_major_choices:
        return Response(
            {"message": "Invalid major."}, status=status.HTTP_400_BAD_REQUEST
        )
    
    valid_grade_choices = [choice[0] for choice in PersonalUser.GRADE_CHOICES]
    if not grade in valid_grade_choices:
        return Response(
            {"message": "Invalid grade."}, status=status.HTTP_400_BAD_REQUEST
        )

    if not password:
        return Response(
            {"message": "Need a nonempty password."}, status=status.HTTP_400_BAD_REQUEST
        )
    
    valid_interest_choices = [choice[0] for choice in PersonalUser.INTEREST_CHOICES]

    interest_list = interest.split(", ")
    for elem in interest_list:
        if not elem in valid_interest_choices:
            return Response(
                {"message": "Invalid interest."}, status=status.HTTP_400_BAD_REQUEST
            )
        
    PersonalUser.objects.create_user(
        nickname=nickname,
        email=email,
        password=password,
        role=role,
        major=major,
        interest=interest,
        grade=grade
    )
        
    try:
        return Response({"message": "created user account"}, status=status.HTTP_200_OK)
    except Exception as e:
        print(e)
        return Response(
            {"error": "An error occurred while creating the user"},
            status=status.HTTP_500_INTERNAL_SERVER_ERROR,
        )

@api_view(['POST'])
def logout(request):
    try:
        request.user.auth_token.delete()
    except Exception as e:
        print(e)
        return Response({"error": "couldn't log out the user"}, status=400)
    return Response({"message": "successfully logged out the user"}, status=200)
