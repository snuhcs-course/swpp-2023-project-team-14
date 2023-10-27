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
    club = UsernicknameSerializer(read_only=True, source="author")

    class Meta:
        model = Post
        fields = (
            "title",
            "content",
            "place",
            "image",
            "is_festival",
            "like_count",
            "time",
            "club",
            "favorite_count",
            "event_durations",
        )
