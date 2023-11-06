# Generated by Django 4.1 on 2023-11-02 05:26

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("user", "0002_alter_personaluser_password"),
    ]

    operations = [
        migrations.AlterField(
            model_name="personaluser",
            name="grade",
            field=models.CharField(
                choices=[
                    ("17", "17학번 이상"),
                    ("18", "18학번"),
                    ("19", "19학번"),
                    ("20", "20학번"),
                    ("21", "21학번"),
                    ("22", "22학번"),
                    ("23", "23학번"),
                    ("Undefined", "Undefined"),
                ],
                default="Undefined",
                max_length=10,
            ),
        ),
        migrations.AlterField(
            model_name="personaluser",
            name="interest",
            field=models.CharField(
                choices=[
                    ("dance", "댄스"),
                    ("meetup", "사교"),
                    ("social", "사회"),
                    ("theater", "연극"),
                    ("music", "음악"),
                    ("sports", "운동"),
                    ("art", "예술"),
                    ("religion", "종교"),
                    ("Undefined", "Undefined"),
                ],
                default="Undefined",
                max_length=10,
            ),
        ),
        migrations.AlterField(
            model_name="personaluser",
            name="major",
            field=models.CharField(
                choices=[
                    ("Business", "경영대학"),
                    ("Engineering", "공과대학"),
                    ("Art", "미술대학"),
                    ("Education", "사범대학"),
                    ("SocialSciences", "사회과학대학"),
                    ("Music", "음악대학"),
                    ("Humanities", "인문대학"),
                    ("NaturalSciences", "자연과학대학"),
                    ("Undefined", "Undefined"),
                ],
                default="Undefined",
                max_length=20,
            ),
        ),
    ]