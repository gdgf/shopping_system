from django.db import models


# Create your models here.

class authuser(models.Model):
    loginID = models.CharField(max_length=64)
    password = models.CharField(max_length=128)


# 员工表
# 没有实现软件对不同等级的员工的权限管理
class StaffTable(models.Model):
    staffID = models.CharField(max_length=20, primary_key=True)  # 员工编号
    name = models.CharField(max_length=6)  # 名字
    sex = models.CharField(max_length=2)  # 性别
    age = models.IntegerField()  # 年龄
    kind = models.CharField(max_length=10)  # 职务
    phone = models.CharField(max_length=12)  # 电话号码
    accountID = models.CharField(max_length=15)  # 银行账号
    # loginID = models.CharField(max_length=64)  # 登录账号
    # spassword = models.CharField(max_length=128)  # 登录摩玛


# 供货商表
class ProfferTable(models.Model):
    supplierID = models.CharField(max_length=20, primary_key=True)  # 供应商ID
    businessman = models.CharField(max_length=6)  # 负责人名字
    goodName = models.CharField(max_length=50)  # 商品名字
    phone = models.CharField(max_length=12)  # 电话号码
    accountID = models.CharField(max_length=15)  # 银行账号
    address = models.CharField(max_length=40)  # 地址
# 库存表

class StorTable(models.Model):
    goods = models.CharField(primary_key=True,max_length=50)  # 名称
    date = models.DateField()  # 日期
    storeNum = models.IntegerField()  # 库存数量
    warnNum = models.IntegerField()  # 警报数量
    # merchID = models.CharField(max_length=20, primary_key=True)  # 商品ID
    # importprice = models.Int1egerField()  # 进价
    # salesPrice = models.IntegerField()  # 售价


# 财务信息表
class FinancialTable(models.Model):
    date = models.DateField(primary_key=True)  # 日期
    dExpenditure = models.IntegerField()  # 日支出
    dIncome = models.IntegerField()  # 日收益
    dProfit = models.IntegerField()  # 日盈利
    mExpenditure = models.IntegerField()  # 月支出
    mIncome = models.IntegerField()  # 月收益
    mProfit = models.IntegerField()  # 月利润

# 进货表
"""
这里商品名称，供应商，进货员应该分别设置成供应商表、员工表的外键，因为他们算是他们的子集，
但实际上又不需要，因为服务端在上传的数据就是根据查询那些表获得的，不可能超出那个范围
"""
class ImportTable(models.Model):
    date = models.DateField(primary_key=True)  # 日期
    goods = models.CharField(max_length=50)  # 商品名称
    worker = models.CharField(max_length=20)  # 进货员
    importprice = models.IntegerField()  # 进价
    quantity = models.IntegerField()  # 数量
    businessman = models.CharField(max_length=6)  # 供应商
    #  totalmoney = models.IntegerField()          # 总价
    #  importID = models.CharField(max_length=20)  # 进货单号
    #  businessman = models.CharField(max_length=20)  # 供应商ID


# 售货表
class SalesTable(models.Model):
    
    date = models.DateField(primary_key=True)  # 日期
    # salesID = models.CharField(max_length=20)  # 进货单号
    goods = models.CharField(max_length=50)      # 商品名称
    salesPrice = models.IntegerField()           # 售价
    quantity = models.IntegerField()             # 数量
    # totalmoney = models.IntegerField()  # 总价
    worker = models.CharField(max_length=20)  # 售货员编号
