import boto3 
import os, environ 
import requests 

from botocore.exceptions import NoCredentialsError
from decouple import config

env = environ.Env(DEBUG=(bool, True))

aws_access_key = config('AWS_ACCESS_KEY_ID')
aws_secret_access_key = config('AWS_SECRET_ACCESS_KEY')
s3_bucket = config('S3_BUCKET')
region_name = config('AWS_DEFAULT_REGION')

s3_client = boto3.client(
    's3',
    aws_access_key_id=aws_access_key,
    aws_secret_access_key=aws_secret_access_key,
    region_name=region_name
)

def upload_file_to_s3(file_name, bucket, object_name=None):
    if object_name is None:
        object_name = file_name
    
    try:
        response = s3_client.upload_file(file_name, bucket, object_name)
        print(f'Response:\n{response}')
    except NoCredentialsError:
        print('Credentials not available')
        return False 
    return True 

def upload_file_to_s3_from_mem(file_obj, bucket, object_name=None):
    if object_name is None:
        object_name = file_obj
    
    try:
        response = s3_client.upload_fileobj(file_obj, bucket, object_name)
        print(f'Response:\n{response}')
    except NoCredentialsError:
        print('Credentials not available')
        return False 
    return True 

def generate_presigned_url(bucket_name, object_key, expiration=3600):
    try:
        response = s3_client.generate_presigned_url('get_object',
            Params={'Bucket': bucket_name,
                    'Key': object_key},
            ExpiresIn=expiration)
    except Exception as e:
        print(e)
        return None
    return response

def upload_file_to_s3_requests(file_obj, presigned_url):
    files = {
        'file': file_obj
    }
    response = requests.put(presigned_url, files=files)
    return response 
    

def download_file_from_s3(bucket, object_name, file_name):
    try:
        s3_client.download_file(bucket, object_name, file_name)
    except NoCredentialsError:
        print("Credentials not available")
        return False
    return True

# examples for file upload and file download
# upload_successful = upload_file_to_s3('dummy.txt', s3_bucket, 's3_file.txt')
# if upload_successful:
#     print('Upload completed successfully!')

# download_successful = download_file_from_s3(s3_bucket, 's3_file.txt', 'local_file.txt')
# if download_successful:
#     print('Download completed successfully!')