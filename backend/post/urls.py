from django.urls import path
from .views import PostListView, PostDetailView, FavoriteView, LikeView, PostFavoriteView, PostRecommendView

app_name = 'post'
urlpatterns = [
    path('', PostListView.as_view(), name='post-list'),

    # 키워드 필터만 사용한 URL
    path('keyword/<str:keyword>/', PostListView.as_view(), name='post-filter-by-keyword'),

    # 축제 여부 필터만 사용한 URL
    path('festival/<int:is_festival>/', PostListView.as_view(), name='post-filter-by-festival'),

    # 날짜 범위 필터만 사용한 URL
    # 홈화면을 위한 single-sided boundary query를 사용한 URL
    path('date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-by-single-sided-date'),

    # start_date만 사용한 URL
    # fixed duplicate date issue
    # fixed issue with posts having the first date less than the start_date, but a different later date larger than the start_date
    path('start_date/<str:start_date>/', PostListView.as_view(), name='post-filter-by-start-date'),

    # end_date만 사용한 URL
    # fixed duplicate date issue. 
    # fixed issue with posts having a date greater than the end_date, but a different previous date less than the end_date
    path('end_date/<str:end_date>/', PostListView.as_view(), name='post-filter-by-end-date'),

    # 키워드와 축제 여부 필터를 사용한 URL
    path('keyword/<str:keyword>/festival/<int:is_festival>/', PostListView.as_view(), name='post-filter-keyword-and-festival'),

    # 키워드와 날짜 범위 필터를 사용한 URL
    path('keyword/<str:keyword>/date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-keyword-and-single-sided-date'),

    # 축제 여부와 날짜 범위 필터를 사용한 URL
    # fixed issue with duplicate items in the given date range.
    path('festival/<int:is_festival>/date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-festival-and-single-sided-date'),

    # 모든 필터를 사용한 URL
    path('keyword/<str:keyword>/festival/<int:is_festival>/date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-all-single-sided-date'),
    
    # fixed date issue. 날짜 >= start_date AND 날짜 <= end_date
    path('exact_date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-single-date'),
    
    # 키워드와 날짜 범위 필터를 사용한 URL
    path('keyword/<str:keyword>/exact_date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-keyword-and-inclusive-range-date'),

    # 축제 여부와 날짜 범위 필터를 사용한 URL
    # fixed issue with duplicate items in the given date range.
    path('festival/<int:is_festival>/exact_date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-festival-and-inclusive-range-date'),

    # 모든 필터를 사용한 URL
    path('keyword/<str:keyword>/festival/<int:is_festival>/exact_date/<str:start_date>/<str:end_date>/', PostListView.as_view(), name='post-filter-all-inclusive-range-date'),
    
    path('<int:post_id>/', PostDetailView.as_view()),
    path('favorite/<int:post_id>/', FavoriteView.as_view()),
    path('like/<int:post_id>/', LikeView.as_view()),
    path('favorite/', PostFavoriteView.as_view()),
    path('recommend/', PostRecommendView.as_view())
]
