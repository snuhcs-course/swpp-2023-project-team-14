from rest_framework import serializers
from .models import Post, Duration, EventDuration
from user.models import PersonalUser


class UsernicknameSerializer(serializers.ModelSerializer):
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


class Postserializer(serializers.ModelSerializer):
    like_count = serializers.IntegerField()
    favorite_count = serializers.IntegerField()
    event_durations = EventDurationSerializer(
        many=True, read_only=True, source="eventduration_set"
    )
    author = UsernicknameSerializer(read_only=True)

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
