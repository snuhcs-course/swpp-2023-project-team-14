import json 
import sys
import uuid
import io

from django.http import JsonResponse 
from pathlib import Path
from rest_framework.views import APIView
from .models import Post, Like, Favorite, Duration, EventDuration, Recommend   
from rest_framework.response import Response
from .serializers import PostSerializer, PostRecommendSerializer, UploadImageSerializer, ImageURLSerializer
from django.db.models import Count, Q
from datetime import date
from rest_framework import status

parent_dir_path = Path(__file__).resolve().parent.parent
sys.path.append(str(parent_dir_path))
import utils


class PostListView(APIView):
    def get(
        self, request, keyword,is_festival, start_date, end_date
    ):
        posts = Post.objects.all()

        if keyword != "":
            posts = posts.filter(Q(title__icontains=keyword) | Q(content__icontains=keyword) | Q(author__nickname__icontains=keyword))
        if int(is_festival) != 2:
            is_festival = bool(int(is_festival))
            posts = posts.filter(is_festival=is_festival)
        if start_date!="" and end_date!="":
            if start_date > end_date:
                return Response(
                    {"detail": "start_date must be earlier than end_date"},
                    status=status.HTTP_400_BAD_REQUEST,
                )
            posts = posts.filter(
                Q(event_durations__event_day__range=[start_date, end_date])
            ).distinct()
        elif start_date!="":
            posts = posts.filter(Q(event_durations__event_day__gte=start_date)).distinct()
        elif end_date!="":
            posts = posts.filter(Q(event_durations__event_day__lte=end_date)).distinct()

        posts = posts.order_by("-like_count")

        serializer = PostSerializer(posts, many=True)
        return Response(serializer.data, status=200)

    def post(self, request):

        title = request.data.get("title")
        content = request.data.get("content")
        author = request.user
        place = request.data.get("place")
        image = request.FILES.get("image")
        old_image = image
        s3_url = ""
        is_festival = request.data.get("is_festival")
        time = request.data.get("time")
        durations = request.data.get("duration")

        if author.role != "Group":
            return Response(
                {"detail": "Authentication credentials not provided"},
                status=status.HTTP_401_UNAUTHORIZED,
            )
        if not title or not durations or not place or not time:
            return Response(
                {"detail": "Please fill in all the blanks"},
                status=status.HTTP_400_BAD_REQUEST,
            )
            
        if image and image.size > 0:   # only do this if "image" is non-empty
            image_data = {'image': image}
            image_serializer = UploadImageSerializer(data=image_data)
            if not image_serializer.is_valid():
                return Response(
                    {"detail": "Image is invalid"},
                    status=status.HTTP_400_BAD_REQUEST,
                )
            image = image_serializer.validated_data['image']
            try: 
                image_name = str(uuid.uuid4())
                image_extension = '.' + str(image.name).split('.')[1]
                full_image_name = image_name + image_extension
                print(f'full_image_name: {full_image_name}')
                presigned_url = utils.generate_presigned_url_img(utils.s3_bucket, full_image_name, expiration=3600, put=True)
                print(f'post: presigned_url\n{presigned_url}')
                
                s3_url = f"https://{utils.s3_bucket}.s3.{utils.region_name}.amazonaws.com/{full_image_name}"
                image_url_serializer = ImageURLSerializer(
                    data={"image_url": s3_url}
                )
                if not image_url_serializer.is_valid():
                    return Response({"error": "issue with image_url_serializer"}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
                url = image_url_serializer.validated_data['image_url']
                print(f'post url:\n{url}')

                try:
                    file_content = old_image.read()
                    upload_response = utils.upload_file_to_s3_requests(file_content, presigned_url)
                    if upload_response.status_code != 200:
                        print("Failed to upload image")
                        return Response({"error": upload_response.text}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
                    else:
                        print("Successfully uploaded image")

                except Exception as e:
                    print(f"Exception during upload: {str(e)}")
                    return Response({"error": str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            except Exception as e:
                return Response({"error": str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        
        try:
            duration_data_json = json.loads(durations)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON format'}, status=400)
    
        post = Post.objects.create(
            title=title,
            content=content,
            time=time,
            place=place,
            image=s3_url,
            is_festival=is_festival,
            author=author,
        )

        for duration_data in duration_data_json:
            print(f'duration_data:\n{duration_data}')

            for it in duration_data.values():
                event_day = it
                try:
                    duration = Duration.objects.get(event_day=event_day)
                except:
                    duration = Duration.objects.create(event_day=event_day)
                    
                post.event_durations.add(duration)
        
        post.save()
        
        serializer = PostSerializer(post)
        return Response(serializer.data, status=201)


class PostDetailView(APIView):
    def get(self, request, post_id):
        try:
            post = Post.objects.get(id=post_id)
        except Post.DoesNotExist:
            return Response({"detail": "Not found."}, status=status.HTTP_404_NOT_FOUND)
        
        is_liked = post.like_users.filter(id=request.user.id).exists()
        is_favorited = post.favorite_users.filter(id=request.user.id).exists()
        post_image = post.image

        if utils.s3_bucket in post_image:
            print(f'image is uploaded to s3. Image_name=\n{post_image}')
            image_name = post.image.split('/')[-1]
            image_first = image_name.split('.')[0]
            image_extension = image_name.split('.')[1]
            presigned_url = utils.generate_presigned_url_img(utils.s3_bucket, image_name, expiration=3600, put=False)
            serializer = PostSerializer(post)
            post_response_data = serializer.data 
            post_response_data['image'] = presigned_url
            elem = post_response_data['image']
            post_response_data['is_liked'] = is_liked
            post_response_data['is_favorited'] = is_favorited
        else:
            print(f'image is not in s3. Image_name=\n{post_image}')
            serializer = PostSerializer(post)
            post_response_data = serializer.data 
            post_response_data['is_liked'] = is_liked
            post_response_data['is_favorited'] = is_favorited

        return Response(post_response_data, status=200)

    def delete(self, request, post_id):
        try:
            post = Post.objects.get(id=post_id)
        except:
            return Response({"detail": "Not found."}, status=status.HTTP_404_NOT_FOUND)
        if request.user != post.author:
            return Response(
                {"detail": "Permission denied"}, status=status.HTTP_401_UNAUTHORIZED
            )
        post.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)


class PostFavoriteView(APIView):
    def get(self, request):
        posts = (
            Post.objects.filter(favorite_users=request.user)
            .order_by("-like_count")
        )
        if posts.count() == 0:
            return Response({"detail": "Not found."}, status=status.HTTP_404_NOT_FOUND)
        serializer = PostSerializer(posts, many=True)
        return Response(serializer.data, status=200)

class PostRecommendView(APIView):
    def get(self, request):
        user = request.user
        today = date.today()
        posts = Post.objects.filter(recommend_users=user,event_durations__event_day__gte=today).order_by("-recommend__score").distinct()
        # scores = Recommend.objects.filter(user=request.user).values('score')
        serializer = PostRecommendSerializer(posts, many=True, context={'request': request})
        return Response(serializer.data, status=200)


class FavoriteView(APIView):
    def patch(self, request, post_id):
        try:
            post = Post.objects.get(id=post_id)
        except:
            return Response({"detail": "Not found."}, status=status.HTTP_404_NOT_FOUND)
        user = request.user
        favorite_list = post.favorite_set.filter(user=user)

        if favorite_list.count() > 0:
            favorite_list.delete()
            post.favorite_count -= 1
        else:
            Favorite.objects.create(user=user, post=post)
            post.favorite_count += 1

        post.save()
        serializer = PostSerializer(post)
        post_response_data = serializer.data
        is_liked = post.like_users.filter(id=request.user.id).exists()
        is_favorited = post.favorite_users.filter(id=request.user.id).exists()
        post_response_data['is_liked'] = is_liked
        post_response_data['is_favorited'] = is_favorited
        return Response(post_response_data, status=200)


class LikeView(APIView):
    def patch(self, request, post_id):
        try:
            post = Post.objects.get(id=post_id)
        except:
            return Response({"detail": "Not found."}, status=status.HTTP_404_NOT_FOUND)
        user = request.user
        like_list = post.like_set.filter(user=user)

        if like_list.count() > 0:
            like_list.delete()
            post.like_count -= 1
        else:
            Like.objects.create(user=user, post=post)
            post.like_count += 1

        post.save()
        serializer = PostSerializer(post)
        post_response_data = serializer.data
        is_liked = post.like_users.filter(id=request.user.id).exists()
        is_favorited = post.favorite_users.filter(id=request.user.id).exists()
        post_response_data['is_liked'] = is_liked
        post_response_data['is_favorited'] = is_favorited
        return Response(post_response_data, status=200)
    

