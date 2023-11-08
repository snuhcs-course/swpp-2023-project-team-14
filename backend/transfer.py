import django
import os, environ 
import pymysql
import pandas as pd
pymysql.install_as_MySQLdb()

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'haengsha.settings')
django.setup()


from user.models import PersonalUser
from post.models import Post, Like, Favorite
from datetime import date

def save_userdata(save_dir, filename='userData.csv'):
    users = PersonalUser.objects.all()
    user_data = []

    for user in users:
        liked_posts = Post.objects.filter(like__user=user).values('title', 'is_festival')
        favorite_posts = Post.objects.filter(favorite__user=user).values('title', 'is_festival')

        liked_academic_posts = [post['title'] for post in liked_posts if not post['is_festival']]
        liked_festival_posts = [post['title'] for post in liked_posts if post['is_festival']]
        favorite_academic_posts = [post['title'] for post in favorite_posts if not post['is_festival']]
        favorite_festival_posts = [post['title'] for post in favorite_posts if post['is_festival']]

        # Concatenate the liked and favorite posts into single strings
        liked_academic_concat = ', '.join(liked_academic_posts)
        liked_festival_concat = ', '.join(liked_festival_posts)
        favorite_academic_concat = ', '.join(favorite_academic_posts)
        favorite_festival_concat = ', '.join(favorite_festival_posts)

        user_info = {
            'user_id': user.id,
            '소속학과': user.major,
            '학번': user.grade,  
            '관심취미': user.interest,
            '좋아요한 학술 행사': liked_academic_concat,
            '좋아요한 공연 행사': liked_festival_concat,
            '관심있는 학술 행사': favorite_academic_concat,
            '관심있는 공연 행사': favorite_festival_concat,
            '학술 행사의 선호도': len(liked_academic_posts)+len(favorite_academic_posts),
            '공연 행사의 선호도': len(liked_festival_posts)+len(favorite_festival_posts)  
        }

        user_data.append(user_info)

    user_df = pd.DataFrame(user_data)
    user_df.to_csv(save_dir+filename, index=False)
    return filename

def put_data():
    file_name = '../recommend/all_recommends.csv'
    df = pd.read_csv(file_name)
    
    posts = Post.objects.all()
    

if __name__ == "__main__":
    save_dir = "./data/"
    ##read_dir = "./machine_output_data/"
    
    save_userdata(save_dir)