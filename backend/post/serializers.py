from rest_framework import serializers
from .models import Post, Duration, EventDuration, Recommend
from user.models import PersonalUser


class UserNicknameSerializer(serializers.ModelSerializer):
    class Meta:
        model = PersonalUser
        fields = [
            "nickname",
        ]

class RecommendSerializer(serializers.ModelSerializer):
    class Meta:
        model = Recommend
        fields = [
            "score",
        ]


class EventDurationSerializer(serializers.ModelSerializer):
    event_day = serializers.DateField(source="duration.event_day")

    class Meta:
        model = EventDuration
        fields = ["event_day"]


class PostSerializer(serializers.ModelSerializer):
    event_durations = EventDurationSerializer(
        many=True, read_only=True, source="eventduration_set"
    )
    author = UserNicknameSerializer(read_only=True)

    class Meta:
        model = Post
        fields = (
            "id",
            "title",
            "is_festival",
            "author",
            "event_durations",
            "place",
            "time",
            "content",
            "image",
            "like_count",
            "favorite_count",
            
        )

class PostRecommendSerializer(serializers.ModelSerializer):
    event_durations = EventDurationSerializer(
        many=True, read_only=True, source="eventduration_set"
    )
    author = UserNicknameSerializer(read_only=True)
    score = serializers.SerializerMethodField()
    def get_score(self, obj):
        user = self.context['request'].user
        recommend = Recommend.objects.filter(user=user, post=obj).first()
        if recommend:
            return recommend.score
        else:
            return 0


    class Meta:
        model = Post
        fields = (
            "id",
            "title",
            "is_festival",
            "author",
            "event_durations",
            "place",
            "time",
            "content",
            "image",
            "like_count",
            "favorite_count",
            "score",
        )
        
class UploadImageSerializer(serializers.Serializer):
    image = serializers.ImageField()

class ImageURLSerializer(serializers.Serializer):
    image_url = serializers.URLField()