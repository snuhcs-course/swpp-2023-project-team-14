from rest_framework.views import APIView
from .models import Post,Like,Favorite,EventDuration
from duration.models import Duration
from rest_framework.response import Response
from .serializers import Postserializer
from django.db.models import Count
from datetime import date
# Create your views here.
class PostListView(APIView):
  def get(self,request):
    posts = Post.objects.filter(event_durations__event_day=date.today()).annotate(like_count=Count('like_users')).order_by('-like_count')
    ## post와 연결된 duration 객체들 중 event_day가 오늘인 것이 하나라도 있으면 값을 반환
    serializer = Postserializer(posts,many=True)
    return Response(serializer.data,status=200)

  def post(self,request):
    title = request.data.get('title')
    content = request.data.get('content')
    place = request.data.get('place')
    image = request.data.get('image')
    is_festival = request.data.get('is_festival')
    post = Post.objects.create(title=title,content=content,place=place,image=image,is_festival=is_festival)
    durations = request.data.get('duration')
    for duration_data in durations:
      event_day = duration_data.get('event_day')
      start_time = duration_data.get('start_time')
      end_time = duration_data.get('end_time')
      duration = Duration.objects.create(event_day=event_day, start_time=start_time, end_time=end_time)
      post.event_durations.add(duration)  # 'post'와 'duration'을 연결
      post.save() 
    serializer = Postserializer(post)
    return Response(serializer.data,status=201)
  
  
class PostDetailView(APIView):
  def get(self,request,post_id):
    post = Post.objects.get(id=post_id)
    serializer = Postserializer(post)
    return Response(serializer.data,status=200)
  
  def delete(self,request,post_id):
    post = Post.objects.get(id=post_id)
    post.delete()
    return Response(status=204)
  
class PostFilterView(APIView):
  def get(self,request,is_festival,date):
    post = Post.objects.filter(is_festival=is_festival,event_durations__event_day=date)
    serializer = Postserializer(post,many=True)
    return Response(serializer.data,status=200)

## 여기까지 테스트 완료
class PostFavoriteView(APIView):
  def get(self,request):
    posts = Post.objects.filter(favorite_users=request.user)
    serializer = Postserializer(posts,many=True)
    return Response(serializer.data,status=200)
  
class PostSearchView(APIView):
  def get(self,request,keyword,category,):
    posts = Post.objects.filter(title__icontains=keyword)
    serializer = Postserializer(posts,many=True)
    return Response(serializer.data,status=200)
  
class FavoriteView(APIView):
  def post(self,request,post_id):
    post = Post.objects.get(id=post_id)
    is_userfavorited = post.favorite_users.filter(id=request.user.id).exists()
    #user가 즐겨찾기 등록을 했는지 확인하는 변수
    if is_userfavorited == True:
      post.favorite_users.get(user=request.user).delete()
      return Response(status=204)
    else:
      Favorite.objects.create(user=request.user,post=post)
    return Response(status=201)
class LikeView(APIView):
  def post(self,request,post_id):
    post = Post.objects.get(id=post_id)
    is_userliked = post.like_users.filter(id=request.user.id).exists()
    #user가 좋아요를 눌렀는지 확인하는 변수
    if is_userliked == True:
      post.like_users.get(user=request.user).delete()
      return Response(status=204)
    else:
      Like.objects.create(user=request.user,post=post)
    return Response(status=201)
    