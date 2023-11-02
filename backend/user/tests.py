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
        self.user = PersonalUser.objects.create_user(
            nickname='some_nickname',
            email='test@snu.ac.kr',
            role='User',
            major='Engineering',
            grade='18',
            interest='music'
        )
        self.user.set_password('passwd')
        self.code = '123456'
        self.cache = cache
        self.cache.set(self.user.email, self.code, timeout=3600)  # Store code for 60 minutes, just for the tests
        self.client.force_authenticate(user=self.user)  # authenticates the request
    
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
            'password': self.user.password
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
        # print(response.content)
        self.assertContains(response, 'The password and the confirmation password do not match.', status_code=400)
