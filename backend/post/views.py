from rest_framework.views import APIView
from .models import Post, Like, Favorite, Duration, EventDuration
from rest_framework.response import Response
from .serializers import Postserializer
from django.db.models import Count, Q
from datetime import date
from rest_framework import status


class PostListView(APIView):
    def get(
        self, request, keyword=None, is_festival=None, start_date=None, end_date=None
    ):
        posts = Post.objects.all()

        if keyword:
            posts = posts.filter(title__icontains=keyword)
        if is_festival is not None:
            posts = posts.filter(is_festival=is_festival)
        if start_date and end_date:
            if start_date > end_date:
                return Response(
                    {"detail": "start_date must be earlier than end_date"},
                    status=status.HTTP_400_BAD_REQUEST,
                )
            posts = posts.filter(
                Q(event_durations__event_day__range=[start_date, end_date])
            )
        elif start_date:
            posts = posts.filter(event_durations__event_day__gte=start_date)
        elif end_date:
            posts = posts.filter(event_durations__event_day__lte=end_date)

        posts = posts.order_by("-like_count")
        if posts.count() == 0:
            return Response(status=status.HTTP_204_NO_CONTENT)

        serializer = Postserializer(posts, many=True)
        return Response(serializer.data, status=200)

    def post(self, request):
        title = request.data.get("title")
        content = request.data.get("content")
        author = request.user
        place = request.data.get("place")
        image = request.data.get("image")
        is_festival = request.data.get("is_festival")
        post = Post.objects.create(
            title=title,
            content=content,
            place=place,
            image=image,
            is_festival=is_festival,
            author=author,
        )
        time = request.data.get("time")
        durations = request.data.get("duration")
        for duration_data in durations:
            event_day = duration_data.get("event_day")
            try:
                duration = Duration.objects.get(event_day=event_day)
            except:
                duration = Duration.objects.create(event_day=event_day)
            post.event_durations.add(duration)
            post.save()

        if author.role != "Group":
            return Response(
                {"detail": "Authentication credentials not provided"},
                status=status.HTTP_401_UNAUTHORIZED,
            )
        if not title or not duration or not place or not time:
            return Response(
                {"detail": "Please fill in all the blanks"},
                status=status.HTTP_400_BAD_REQUEST,
            )
        serializer = Postserializer(post)
        return Response(serializer.data, status=201)


class PostDetailView(APIView):
    def get(self, request, post_id):
        try:
            post = Post.objects.get(id=post_id)
        except Post.DoesNotExist:
            return Response({"detail": "Not found."}, status=status.HTTP_404_NOT_FOUND)

        is_liked = post.like_users.filter(id=request.user.id).exists()
        is_favorite = post.favorite_users.filter(id=request.user.id).exists()

        serializer = Postserializer(post)
        data = serializer.data
        data['is_liked'] = is_liked
        data['is_favorite'] = is_favorite

        return Response(data, status=200)

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
        serializer = Postserializer(posts, many=True)
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

        serializer = Postserializer(post)
        return Response(serializer.data, status=200)


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

        serializer = Postserializer(post)
        return Response(serializer.data, status=200)
