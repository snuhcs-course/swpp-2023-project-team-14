import django
import os, environ 
import pymysql
import pandas as pd
import argparse
from django.utils import timezone

pymysql.install_as_MySQLdb()

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'haengsha.settings')
django.setup()


from user.models import PersonalUser
from post.models import Post, Like, Favorite, Recommend
from datetime import date

def save_user_data(save_dir, filename='userData.csv'):
    users = PersonalUser.objects.filter(role='User')
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

def save_event_data(save_dir, filename='eventData.csv'):
    today = timezone.now().date()
    posts = Post.objects.filter(event_durations__event_day__gte=today).distinct()
    event_data = []

    for post in posts:
        category = "학술" if not post.is_festival else "공연"
        event_duration = post.event_durations.first()         # Retrieve the starting date only from event_duration

        post_info = {
            'post_id': post.id,
            '구분': category,
            '행사명': post.title,
            '날짜': event_duration.event_day if event_duration else "",
            '장소': post.place,
            '주최자': post.author.nickname if post.author else "",
            '내용': post.content,
        }

        event_data.append(post_info)

    event_df = pd.DataFrame(event_data)
    event_df.to_csv(save_dir+filename, index=False)
    return filename


def put_recommends(save_dir, filename='all_recommends_index.csv', save_filename='transformed_recommends.csv'):
    df = pd.read_csv(save_dir+filename)
    transformed_data = []

    for _, row in df.iterrows():
        userIdx = row['userIdx']
        user = PersonalUser.objects.get(pk=userIdx)

        for col in df.columns[2:]:
            score = int(col.replace('Recommend', ''))
            postIdx = row[col]

            if postIdx == -1: #Under 10 events were passed through the machine
                continue

            try:
                existing_recommend = Recommend.objects.get(user=user, post=postIdx, score=score)
                #print("existing", postIdx, user, score)

            except Recommend.DoesNotExist:
                post = Post.objects.get(pk=postIdx)
                recommend_instance = Recommend(user=user, post=post, score=score)
                recommend_instance.save()

            transformed_data.append([score, postIdx, userIdx])

    transformed_df = pd.DataFrame(transformed_data, columns=['score', 'postIdx', 'userIdx'])
    transformed_df.to_csv(save_dir+save_filename, index=True)
    return transformed_df

def delete_future_recommendations():
    today = timezone.now().date()
    future_recommendations = Recommend.objects.filter(post__event_durations__event_day__gte=today)
    future_recommendations.delete()

# Util Function to check whether recommendation exists
def recommendation_exists(score, postId, userIdx):
    try:
        user = PersonalUser.objects.get(pk=userIdx)
        post = Post.objects.get(pk=postId)
        existing_recommend = Recommend.objects.get(user=user, post=post, score=score)
        return True
    except (PersonalUser.DoesNotExist, Post.DoesNotExist, Recommend.DoesNotExist):
        return False

def main():
    parser = argparse.ArgumentParser(description='Process recommendations.')
    parser.add_argument('-c', '--collect', action='store_true', help='collect input data for machine input')
    parser.add_argument('-l', '--load', action='store_true', help='Load output from machine to DB')
    args = parser.parse_args()

    save_dir = "./data/"
    if args.collect:
        save_user_data(save_dir) #save userData.csv
        save_event_data(save_dir) #save eventData.csv

    if args.load:
        delete_future_recommendations() #this is to renew the recommendation score!
        df = put_recommends(save_dir)
        # save df to log file
        with open('logfile.log', 'w') as f:
            f.write(df.to_string()) 
    
if __name__ == '__main__':
    main()
