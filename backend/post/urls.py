from django.urls import path,re_path
from .views import PostListView, PostDetailView, FavoriteView, LikeView, PostFavoriteView, PostRecommendView

app_name = 'post'
urlpatterns = [
    #게시글 올릴 때 사용할 url
    path('', PostListView.as_view(), name='post-list'),

    # 모든 필터를 사용한 URL
    re_path(r'^keyword/(?P<keyword>.*)/festival/(?P<is_festival>\d+)/date/(?P<start_date>[\d-]*)/(?P<end_date>[\d-]*)/$', PostListView.as_view(), name='post-filter-all'),
    path('<int:post_id>/', PostDetailView.as_view(), name='post-detail'),
    path('favorite/<int:post_id>/', FavoriteView.as_view(), name='post-favorite'),
    path('like/<int:post_id>/', LikeView.as_view(), name='post-like'),
    path('favorite/', PostFavoriteView.as_view(), name='post-favorite-list'),
    path('recommend/', PostRecommendView.as_view(), name='post-recommend-list')
]
