from rest_framework import serializers
from .models import Post, EventDuration
from user.models import PersonalUser


class UserNicknameSerializer(serializers.ModelSerializer):
    class Meta:
        model = PersonalUser
        fields = [
            "nickname",
        ]


class EventDurationSerializer(serializers.ModelSerializer):
    event_day = serializers.DateField(source="duration.event_day")

    class Meta:
        model = EventDuration
        fields = ["event_day"]


class PostSerializer(serializers.ModelSerializer):
    like_count = serializers.IntegerField()
    favorite_count = serializers.IntegerField()
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

class UploadImageSerializer(serializers.Serializer):
    image = serializers.ImageField()

class ImageURLSerializer(serializers.Serializer):
    image_url = serializers.URLField()