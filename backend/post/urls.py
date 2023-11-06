from django.urls import path
from .views import PostListView, PostDetailView, FavoriteView, LikeView, PostFavoriteView

app_name = 'post'
urlpatterns = [
    path('', PostListView.as_view(), name='post-list'),

    # 키워드 필터만 사용한 URL
    path('keyword/<str:keyword>/', PostListView.as_view(), name='post-filter-by-keyword'),

    # 축제 여부 필터만 사용한 URL
    path('festival/<int:is_festival>/', PostListView.as_view(), name='post-filter-by-festival'),

    # 날짜 범위 필터만 사용한 URL
    path('date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-by-date'),

    # start_date만 사용한 URL
    path('start_date/<str:start_date>/', PostListView.as_view(), name='post-filter-by-start-date'),

    # end_date만 사용한 URL
    path('end_date/<str:end_date>/', PostListView.as_view(), name='post-filter-by-end-date'),

    # 키워드와 축제 여부 필터를 사용한 URL
    path('keyword/<str:keyword>/festival/<int:is_festival>/', PostListView.as_view(), name='post-filter-keyword-and-festival'),

    # 키워드와 날짌 범위 필터를 사용한 URL
    path('keyword/<str:keyword>/date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-keyword-and-date'),

    # 축제 여부와 날짜 범위 필터를 사용한 URL
    path('festival/<int:is_festival>/date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-festival-and-date'),

    # 모든 필터를 사용한 URL
    path('keyword/<str:keyword>/festival/<int:is_festival>/date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-all'),
    path('<int:post_id>/', PostDetailView.as_view()),
    path('favorite/<int:post_id>/', FavoriteView.as_view()),
    path('like/<int:post_id>/', LikeView.as_view()),
    path('favorite/', PostFavoriteView.as_view()),

    # 새 행사 등록 URL
    path('new_event/', PostListView.as_view(), name='create-new-event')
]
