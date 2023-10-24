from django.urls import path
from .views import PostListView, PostDetailView, FavoriteView, LikeView, PostFavoriteView

app_name = 'post'
urlpatterns = [
    path('<int:post_id>/', PostDetailView.as_view()),
    path('favorite/<int:post_id>/', FavoriteView.as_view()),
    path('like/<int:post_id>/', LikeView.as_view()),
    path('favorite/', PostFavoriteView.as_view()),
    path('/<str:keyword>/<int:is_festival>/<str:start_date>/<str:end_date>/', PostListView.as_view()),
]
