import django
import os, environ 
import pymysql
import pandas as pd
pymysql.install_as_MySQLdb()

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'haengsha.settings')
django.setup()



from post.models import Post
from user.models import PersonalUser

def get_data():
    myset = PersonalUser.objects.filter(role='User')
    myset_list = list(myset)
    print(myset_list)
    return myset_list 

def put_data():
    file_name = '../recommend/all_recommends.csv'
    df = pd.read_csv(file_name)
    
    posts = Post.objects.all()
    

get_data()