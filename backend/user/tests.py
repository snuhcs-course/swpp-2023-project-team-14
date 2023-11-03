from django.test import TestCase
from django.core.cache import cache
from django.urls import reverse 
from rest_framework import status
from rest_framework.test import APIClient
from .models import PersonalUser

# Create your tests here.
class SignedInUserAPITest(TestCase):
    @classmethod
    def setUpClass(self):
        super().setUpClass()
        self.client = APIClient()
        self.password = 'pass1'
        self.user = PersonalUser.objects.create_user(
            nickname='some_nickname',
            email='test@snu.ac.kr',
            password=self.password,
            role='User',
            major='Engineering',
            grade='18',
            interest='music'
        )
        self.code = '123456'
        self.cache = cache
        self.cache.set(self.user.email, self.code, timeout=3600)  # Store code for 60 minutes, just for the tests
        self.client.login(username=self.user.email, password=self.password)  # authenticates the request
    
    @classmethod
    def tearDownClass(self):
        user_to_delete = PersonalUser.objects.get(email=self.user.email)
        user_to_delete.delete()
        self.cache.delete(self.user.email)
        super().tearDownClass()
    
    # Test Cases for Sign-In
    def test_signin_success(self):
        request = {
            'email': self.user.email,
            'password': self.password
        }
        response = self.client.post(reverse('signin'), request)
        print(response.content)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
    
    def test_signin_empty_email(self):
        request = {
            'email': '',
            'password': 'asdfasdf'
        }
        response = self.client.post(reverse('signin'), request)
        self.assertContains(response, 'Both email and password are required fields.', status_code=400)
    
    def test_signin_empty_password(self):
        request = {
            'email': self.user.email,
            'password': ''
        }
        response = self.client.post(reverse('signin'), request)
        self.assertContains(response, 'Both email and password are required fields.', status_code=400)

    def test_signin_failure(self):
        request = {
            'email': self.user.email,
            'password': 'wrongpassword'
        }
        response = self.client.post(reverse('signin'), request)
        self.assertContains(response, 'There is no account with the given email or password.', status_code=401)

    # Test Cases for Email Verification of Signed-In User
    def test_verify_email_signin_success(self):
        response = self.client.post(reverse('verify_email_signin'), {'email': self.user.email})
        self.assertContains(response, 'A verification code has been sent to your email. Please enter it to verify your account.')

    def test_verify_email_signin_failure(self):
        response = self.client.post(reverse('verify_email_signin'), {'email': 'nonexistent@snu.ac.kr'})
        self.assertContains(response, 'A user account with the given email does not exist.', status_code=404)

    def test_verify_email_signin_empty_email(self):
        response = self.client.post(reverse('verify_email_signin'), {'email': ''})
        self.assertContains(response, 'The email field is required.', status_code=400)

    # Test Cases for Code Verification
    def test_verify_code_success(self):
        correct_code = str(cache.get(self.user.email))
        request = {
            'email': self.user.email, 
            'code': correct_code
        }
        response = self.client.post(reverse('verify_code_signin'), request)
        self.assertContains(response, 'Authentication code verified successfully.')

    def test_verify_code_failure(self):
        request = {
            'email': self.user.email,
            'code': '654321'
        }
        response = self.client.post(reverse('verify_code_signin'), request)
        self.assertContains(response, 'The authentication code is invalid or has expired.', status_code=401)

    def test_change_password_success(self):
        request = {
            'email': self.user.email,
            'password': 'newpassword1',
            'password_confirm': 'newpassword1'
        }
        response = self.client.post(reverse('change_password'), request)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_change_password_too_short(self):
        request = {
            'email': self.user.email,
            'password': 'sho',
            'password_confirm': 'sho'
        }
        response = self.client.post(reverse('change_password'), request)
        self.assertContains(response, 'The password must have at least 4 characters and at most 20 characters.', status_code=400)

    def test_change_password_too_long(self):
        long_password = 'a' * 101  # Assuming your password max length is 100
        request = {
            'email': self.user.email,
            'password': long_password,
            'password_confirm': long_password
        }
        response = self.client.post(reverse('change_password'), request)
        self.assertContains(response, 'The password must have at least 4 characters and at most 20 characters.', status_code=400)

    def test_change_password_not_alphanumeric(self):
        request = {
            'email': self.user.email,
            'password': '---asdf',
            'password_confirm': '---asdf'
        }
        response = self.client.post(reverse('change_password'), request)
        self.assertContains(response, 'The password must consist of only alphanumeric characters.', status_code=400)

    def test_change_password_no_alphabetic_character(self):
        request = {
            'email': self.user.email,
            'password': '12345678',
            'password_confirm': '12345678'
        }
        response = self.client.post(reverse('change_password'), request)
        self.assertContains(response, 'The password must contain at least 1 alphabetic character.', status_code=400)

    def test_change_password_no_digit(self):
        request = {
            'email': self.user.email,
            'password': 'passwordo',
            'password_confirm': 'passwordo'
        }
        response = self.client.post(reverse('change_password'), request)
        self.assertContains(response, 'The password must contain at least 1 digit.', status_code=400)

    def test_change_password_password_confirmation_no_match(self):
        request = {
            'email': self.user.email,
            'password': 'newpasswor1',
            'password_confirm': 'diffpasswor2'
        }
        response = self.client.post(reverse('change_password'), request)
        self.assertContains(response, 'The password and the confirmation password do not match.', status_code=400)

class SignUpUserAPITest(TestCase):
    @classmethod
    def setUpClass(self):
        super().setUpClass()
        self.client = APIClient()
        self.password = 'pass1'
        self.user = PersonalUser.objects.create_user(
            nickname='nickname',
            email='yolo@snu.ac.kr',
            password=self.password,
            role='User',
            major='Engineering',
            grade='18',
            interest='music'
        )

    @classmethod
    def tearDownClass(self):
        user_to_delete = PersonalUser.objects.get(email=self.user.email)
        user_to_delete.delete()
        super().tearDownClass()
    
    def test_signup_user_success(self):
        request = {
            'email': 'user@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "Engineering",
            "grade": "23",
            "password": "whatagreatpasswd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_signup_user_failure_no_email(self):
        request = {
            'email': '',
            "nickname": "lol",
            "role": "User",
            "major": "Engineering",
            "grade": "23",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Need a nonempty email.', status_code = 400)

    def test_signup_user_failure_short_nickname(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "l",
            "role": "User",
            "major": "Engineering",
            "grade": "23",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'The nickname must be at least 2 characters and at most 10 characters long.', status_code = 400)

    def test_signup_user_failure_long_nickname(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lasudofusaiodfoasd",
            "role": "User",
            "major": "Engineering",
            "grade": "23",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'The nickname must be at least 2 characters and at most 10 characters long.', status_code = 400)

    def test_signup_user_failure_no_role(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "",
            "major": "Engineering",
            "grade": "23",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Need a nonempty role.', status_code = 400)

    def test_signup_user_failure_no_major(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "",
            "grade": "23",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Need a nonempty major.', status_code = 400)

    def test_signup_user_failure_invalid_major(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "Etymology",
            "grade": "23",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Invalid major.', status_code = 400)

    def test_signup_user_failure_no_grade(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "Etymology",
            "grade": "",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Need a nonempty grade.', status_code = 400)

    def test_signup_user_failure_invalid_grade(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "Engineering",
            "grade": "24",
            "password": "passwd2",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        print(f'invalid grade response:\n{response}')
        self.assertContains(response, 'Invalid grade.', status_code = 400)
    
    def test_signup_user_failure_no_interest(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "Engineering",
            "grade": "23",
            "password": "passwd2",
            "interest": ""
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Need a nonempty interest.', status_code = 400)
    
    def test_signup_user_failure_invalid_interest(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "Engineering",
            "grade": "23",
            "password": "passwd2",
            "interest": "rapping"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Invalid interest.', status_code = 400)
    
    def test_signup_user_failure_no_password(self):
        request = {
            'email': 'test@snu.ac.kr',
            "nickname": "lol",
            "role": "User",
            "major": "Engineering",
            "grade": "23",
            "password": "",
            "interest": "music"
        }
        response = self.client.post(reverse('signup'), request)
        self.assertContains(response, 'Need a nonempty password.', status_code = 400)

    def test_verify_snu_email_signup_success(self):
        request = {'email': 'user@snu.ac.kr'}
        response = self.client.post(reverse('verify_email_signup'), request)
        self.assertContains(response, 'A verification code has been sent to your email. Please enter it to verify your account.')

    def test_verify_snu_email_signup_failure(self):
        url = reverse('verify_email_signup')
        data = {'email': 'yolo@snu.ac.kr'}
        response = self.client.post(url, data)
        print(f'signup_failure response:\n{response}')
        self.assertContains(response, 'A user account with the given email already exists.', status_code = 401)

    def test_signup_nickname_success(self):
        url = reverse('check_nickname')
        data = {'nickname': 'uniqueuser'}
        response = self.client.post(url, data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_signup_nickname_failure(self):
        url = reverse('check_nickname')
        data = {'nickname': 'nickname'}  # Assuming 'takennickname' is already taken
        response = self.client.post(url, data)
        print(f'nickname_failure response:\n{response}')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)