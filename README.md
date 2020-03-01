目前服务端和客户端要运行在同一局域网下面。所以需要修改客户端base模块HttpUtil.java中的IP.

1. MMSAndroid是android客户端，运行环境android studio,首先注册账户后
   才能用，使用过程中一直需要联网，因为没有在android手机本地保存数据。
2. MMSServer是服务端, 可以在pycharm中运行，也可以在命令行中运行(
   需要安装Django环境)。另外数据库也要建立,并在相应的位置更改配置。这里数
   据库名为MY_MMS.
