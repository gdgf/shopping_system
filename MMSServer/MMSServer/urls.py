"""MMSServer URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from MMS.views import *

urlpatterns = [
    path('admin/', admin.site.urls),
    path('',index),
    path('MMS/register', auth_user_register),
    path('MMS/login', auth_user_login),
    path('MMS/addstaff',add_staff),
    path('MMS/addsupplier',add_supplier),
    path('MMS/looksupplier',look_supplier),
    path('MMS/lookstaff', look_staff),
    path('MMS/look_profferinfor', look_profferinfor),
    path('MMS/return_whichproffer', return_whichproffer),
    path('MMS/get_workerjin', get_workerjin),
    path('MMS/get_workershou', get_workershou),
    path('MMS/get_goods',get_goods),
    path('MMS/importgoods', importgoods),
    path('MMS/salse',salse),
    path('MMS/lookprecording',look_import),
    path('MMS/looksrecording', look_salse),
    path('MMS/lookstore', look_store),
    path('MMS/monthach',get_monthach),
    path('MMS/get_days',get_days),
    path('MMS/get_months',get_months),


]
