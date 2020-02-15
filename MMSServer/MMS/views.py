from django.db import connection,connections
from django.http import HttpRequest, HttpResponse
from rest_framework.renderers import JSONRenderer
from django.views.decorators.csrf import csrf_exempt
from MMS.models import *
from  MMS.tests import  *

from django.core import serializers


# Create your views here.

def index(request):
    return HttpResponse("Hello, world!")


@csrf_exempt
def auth_user_register(request):
    if request.method == 'POST':
        data = request.POST
        # serializer = auth_userSerializer(data=data)

        name = data['username']
        posswd = data['password']
        if (posswd != 0):
            newuser = authuser(loginID=name, password=posswd)
            newuser.save()
            return JSONResponse({'register': 222})
    return HttpResponse(status=404)
    
@csrf_exempt
def auth_user_login(request):
    if request.method == 'POST':
        data = request.POST

        name = data['username']
        posswd = data['password']
        if name != 0 and posswd != 0:
            user = authuser.objects.get(loginID=name)
            if (user.password == posswd):
                return JSONResponse({'login': 111})
    return HttpResponse(status=404)


@csrf_exempt
def add_staff(request):
    if request.method == 'POST':
        data = request.POST

        nstaffID = data['staffID']
        nname = data['name']
        nsex = data['sex']
        nage = data['age']
        nkind = data['kind']
        nphone = data['phone']
        naccount = data['account']

        """
        这里应该增加验证，如果编号相同的添加不成功
        """
        newStaff = StaffTable(staffID=nstaffID, name=nname, sex=nsex, kind=nkind, age=nage, phone=nphone,
                              accountID=naccount)
        newStaff.save()
    return JSONResponse({'upload': 666})


@csrf_exempt
def add_supplier(request):
    if request.method == 'POST':
        data = request.POST

        nname = data['name']
        sp = data['sp']
        good = data['gd']
        add = data['ad']
        nphone = data['ph']
        naccount = data['ac']
        """
              这里应该增加验证，如果编号相同的添加不成功
        """
        newStaff = ProfferTable(supplierID=sp, businessman=nname, goodName=good, address=add, phone=nphone,
                                accountID=naccount)
        newStaff.save()
    return JSONResponse({'upload': 777})


@csrf_exempt
def look_supplier(request):
    if request.method == 'GET':
        """
        data = ProfferTable.objects.all()
        isdict = json.loads(serializers.serialize('json', data))
        """
        cursor = connection.cursor()
        sql='SELECT * FROM MMS_proffertable'
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))
        #isdict = json.loads(serializers.serialize('json', values))
        #return HttpResponse(json.dumps(isdict, ensure_ascii=False), con1tent_type='application/json', charset='utf-8')


@csrf_exempt
def look_staff(request):
    if request.method == 'GET':
        cursor = connection.cursor()
        sql='SELECT * FROM MMS_stafftable'
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))

@csrf_exempt
# 查看进货记录表
def look_import(request):
    if request.method == 'GET':
        cursor = connection.cursor()
        sql='SELECT * FROM MMS_importtable'
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values,cls=CJsonEncoder))

@csrf_exempt
# 查看库存表
def look_store(request):
    if request.method == 'GET':
        cursor = connection.cursor()
        sql='SELECT * FROM MMS_stortable'
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values,cls=CJsonEncoder))



@csrf_exempt
# 查看售货表
def look_salse(request):
    if request.method == 'GET':
        cursor = connection.cursor()
        sql='SELECT * FROM MMS_salestable'
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values,cls=CJsonEncoder))



@csrf_exempt
# 查询商家和商品
def look_profferinfor(request):
    if request.method == 'GET':
        """
        data = ProfferTable.objdects.all()
        isdict = json.loads(serializers.serialize('json', data))
        """
        cursor = connection.cursor()
        sql='SELECT distinct goodname,businessman FROM MMS_proffertable'
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))

@csrf_exempt
# 从库存表查询物品及其数量，数量用于限制能购买的数量，和报警
def get_goods(request):
    if request.method == 'GET':
        """
        data = ProfferTable.objdects.all()
        isdict = json.loads(serializers.serialize('json', data))
        """
        cursor = connection.cursor()
        sql='SELECT goods,storeNum,warnNum FROM MMS_stortable'
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))



@csrf_exempt
# 根据商品查询对应的商家
def return_whichproffer(request):
    if request.method == 'POST':

        data = request.POST
        goods=data['goods']
        cursor = connection.cursor()
        # 这种写法不成熟，应该改为变量自动填充的方式
        sql='SELECT businessman FROM MMS_proffertable WHERE goodName='+"'"+goods+"'"
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))

@csrf_exempt
# 从员工表中查询进货员
def get_workerjin(request):
    if request.method == 'GET':
        cursor = connection.cursor()
        sql="SELECT name FROM MMS_stafftable WHERE kind='进货员'"
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))

@csrf_exempt
# 从员工表中查询售货员
def get_workershou(request):
    if request.method == 'GET':
        cursor = connection.cursor()
        sql="SELECT name FROM MMS_stafftable WHERE kind='售货员'"
        cursor.execute(sql)
        values=cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))


@csrf_exempt
# 进货
def importgoods(request):
    if request.method == 'POST':

        data = request.POST
        goods=data['goods']
        sup=data['supplier']
        price=data['price']
        num=data['num']
        date1=data['date']
        worker1=data['worker']
       # 更新进货表
        new=ImportTable(date=date1,goods=goods,worker=worker1,quantity=num,businessman=sup,importprice=price)

       # 更新库存表
        cursor = connection.cursor()
        sql = 'SELECT storeNum FROM MMS_stortable WHERE  goods='+"'"+goods+"'"
        cursor.execute(sql)
        values=cursor.fetchone()
        if values!=None:    # 货物存在时，需要再加上刚进货物的数量，否则不用加
           num=values[0]+int(num)
        new1=StorTable(date=date1,goods=goods,storeNum=num,warnNum=10)
        new.save()
        new1.save()
        return JSONResponse({'upload': 000})

@csrf_exempt
# 售货
def salse(request):
    if request.method == 'POST':

        data = request.POST
        goods=data['goods']
        price=data['price']
        num=data['num']
        date=data['date']
        worker=data['worker']
         # 更新售货表
        new=SalesTable(date=date,goods=goods,worker=worker,quantity=num,salesPrice=price)
        # 更新库存表
        cursor = connection.cursor()
        sql = 'SELECT storeNum FROM MMS_stortable WHERE  goods='+"'"+goods+"'"
        cursor.execute(sql)
        values=cursor.fetchone()
        num=values[0]-int(num)
        new1=StorTable(date=date,goods=goods,storeNum=num,warnNum=10)
        new.save()
        new1.save()
        return JSONResponse({'upload': 222})


@csrf_exempt
# 销售员月业绩
def get_monthach(request):
    if request.method == 'POST':

        data = request.POST
        year=data['year']
        month=data['month']
        wor=data['worker']
        next=str(int(month)+1)
        print(year)
        print(month)
        print(wor)
        print(next)
        # 查询售货表
        cursor = connection.cursor()
        # 需要更改为变量自动填充的方式
        sql = 'SELECT goods,quantity FROM MMS_salestable ' \
              'WHERE  worker='+ "'"+ wor +"'"+\
              ' and date >'+"'"+str(year)+'-'+str(month)+'-00'+ "'"\
              +'and date<'+"'"+str(year)+"-"+str(next)+'-00'+"'"

        cursor.execute(sql)
        values = cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))

@csrf_exempt
# 得到日盘存
def get_days(request):
    if request.method == 'POST':

        data = request.POST
        year=data['year']
        month=data['month']
        day=data['day']

        print(year)
        print(month)
        print(day)
        # 查询售货表
        cursor = connection.cursor()
        sql = 'SELECT goods,quantity FROM MMS_salestable ' \
              'WHERE  date =' + "'" + str(year) + '-' + str(month) + '-' + str(day) + "'"

        cursor.execute(sql)
        values = cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))

@csrf_exempt
# 得到一个月盘存
def get_months(request):
    if request.method == 'POST':

        data = request.POST
        year=data['year']
        month=data['month']
        next = str(int(month) + 1)
        print(year)
        print(month)
        print(next)
        # 查询售货表
        cursor = connection.cursor()
        sql = 'SELECT goods,quantity FROM MMS_salestable ' \
              'WHERE date >' + "'" + str(year) + '-' + str(month) + '-00' + "'" \
              + 'and date<' + "'" + str(year) + "-" + str(next) + '-00' + "'"

        cursor.execute(sql)
        values = cursor.fetchall()
        for item in values:
            print(item)
        return HttpResponse(json.dumps(values))

class JSONResponse(HttpResponse):
    """
    用于返回JSON数据.
    """
    def __init__(self, data, **kwargs):
        content = JSONRenderer().render(data)
        kwargs['content_type'] = 'application/json'
        super(JSONResponse, self).__init__(content, **kwargs)


