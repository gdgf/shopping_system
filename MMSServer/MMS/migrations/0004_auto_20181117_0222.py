# Generated by Django 2.1.3 on 2018-11-17 02:22

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('MMS', '0003_auto_20181116_1412'),
    ]

    operations = [
        migrations.RenameField(
            model_name='salestable',
            old_name='goodName',
            new_name='goods',
        ),
    ]
