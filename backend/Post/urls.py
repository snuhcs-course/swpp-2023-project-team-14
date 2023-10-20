from django.urls import path
from .views import PostListView, PostDetailView, PostFilterView,FavoriteView

app_name = 'post'
urlpatterns = [
    path('', PostListView.as_view()),
    path('<int:post_id>/', PostDetailView.as_view()),
    path('filter/<int:is_festival>/<str:date>/', PostFilterView.as_view()),
    path('favorite/<int:post_id>/', FavoriteView.as_view()),
]
