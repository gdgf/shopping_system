# Generated by Django 2.1.3 on 2018-11-16 12:19

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='authuser',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('loginID', models.CharField(max_length=64)),
                ('password', models.CharField(max_length=128)),
            ],
        ),
        migrations.CreateModel(
            name='FinancialTable',
            fields=[
                ('date', models.DateField(primary_key=True, serialize=False)),
                ('dExpenditure', models.IntegerField()),
                ('dIncome', models.IntegerField()),
                ('dProfit', models.IntegerField()),
                ('mExpenditure', models.IntegerField()),
                ('mIncome', models.IntegerField()),
                ('mProfit', models.IntegerField()),
            ],
        ),
        migrations.CreateModel(
            name='ImportTable',
            fields=[
                ('date', models.DateField(primary_key=True, serialize=False)),
                ('goods', models.CharField(max_length=50)),
                ('woeker', models.CharField(max_length=20)),
                ('importprice', models.IntegerField()),
                ('quantity', models.IntegerField()),
                ('businessman', models.CharField(max_length=6)),
            ],
        ),
        migrations.CreateModel(
            name='ProfferTable',
            fields=[
                ('supplierID', models.CharField(max_length=20, primary_key=True, serialize=False)),
                ('businessman', models.CharField(max_length=6)),
                ('goodName', models.CharField(max_length=50)),
                ('phone', models.CharField(max_length=12)),
                ('accountID', models.CharField(max_length=15)),
                ('address', models.CharField(max_length=40)),
            ],
        ),
        migrations.CreateModel(
            name='SalesTable',
            fields=[
                ('date', models.DateField(primary_key=True, serialize=False)),
                ('salesID', models.CharField(max_length=20)),
                ('goodName', models.CharField(max_length=50)),
                ('salesPrice', models.IntegerField()),
                ('quantity', models.IntegerField()),
                ('totalmoney', models.IntegerField()),
                ('staffID', models.CharField(max_length=20)),
            ],
        ),
        migrations.CreateModel(
            name='StaffTable',
            fields=[
                ('staffID', models.CharField(max_length=20, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=6)),
                ('sex', models.CharField(max_length=2)),
                ('age', models.IntegerField()),
                ('kind', models.CharField(max_length=10)),
                ('phone', models.CharField(max_length=12)),
                ('accountID', models.CharField(max_length=15)),
            ],
        ),
        migrations.CreateModel(
            name='StorTable',
            fields=[
                ('goodName', models.CharField(max_length=50, primary_key=True, serialize=False)),
                ('date', models.DateField()),
                ('storeNum', models.IntegerField()),
                ('warnNum', models.IntegerField()),
            ],
        ),
    ]
