服务部署

1、把 doc/install 目录下的 httpd.conf 文件合并到 Apache 配置文件中去。
2、启动 Apache 服务器。

目录说明

app-demos-server
|- bin              : 可执行脚本入口
|- dat              : 数据目录
|- doc              : 文档目录
|  |- install       : 服务器配置文件
|- etc              : 配置文件目录
|- lib              : 逻辑类库
|  |- Demos
|     |- App        : 应用逻辑（Controller）
|     |- Cli        : 后台脚本逻辑
|     |- Dao        : 数据库操作类
|     |- Util       : 工具类库
|- tpl
|  |- server        : 接口站点模板（View）
|  |- website       : 前台站点模板（View）
|- www
   |- server        : 接口站点（DocumentRoot）
   |- website       : 前台站点（DocumentRoot）