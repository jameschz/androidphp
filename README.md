# Android和PHP最佳实践 - 官方站

## 重要通知

> 欢迎大家加入Android和PHP技术交流群：122860896 

> 本书实例已经更新，加入了图片选择和上传功能，安装变化参考以下面说明

> Hush Framework 已正式迁至 Github：https://github.com/jameschz/hush ，感谢大家继续支持！

## 图书资源下载

1、Xampp 开发环境（xampp.zip）下载：http://pan.baidu.com/s/1o69gTGe

2、微博实例完整源码包（android-php-source.zip）下载：http://pan.baidu.com/s/1c0xVZeo

3、Hush Framework 框架源码及相关资源：https://github.com/jameschz/hush

4、本书其他 Android 客户端的实例源码也包含在微博实例完整源码包中，见 android-php-source/androidphp/ 下的 opengl 和 special 项目

## 微博实例源码安装步骤（for Windows）

1、下载 “图书资源下载” 列表中前两项的内容。 

2、解压下载到的 Xampp 开发环境（xampp.zip）到 D:\xampp 目录下。 

3、点击 D:\xampp 目录下的 setup_xampp.bat 文件，输入1，然后回车，安装好 Xampp。 

4、把 D:\xampp\php 和 D:\xampp\mysql\bin 目录加入到 Windows 系统环境变量 Path 中去。 

5、解压下载到的微博实例完整源码包（android-php-source.zip），并拷贝文件到 D: 盘，比如：D:\android-php-source。 

6、使用源码目录（D:\android-php-source）下的 httpd-vhosts.conf 文件覆盖 D:\xampp\apache\conf\extra 目录下的同名 Apache 配置文件。 

7、在 Xampp 控制台（D:\xampp\xampp-control.exe）启动 Xampp 的 Apache 和 MySQL。 

8、打开系统命令提示符，进入 D:\android-php-source\hush-framework\hush-app\bin 目录，运行“hush sys init”命令初始化 Hush Framework 框架实例，如果出现报错请参考以下“常见问题解答”部分。 

9、打开系统命令提示符，进入 D:\android-php-source\androidphp\server\bin 目录，运行“cli sys init”命令初始化微博应用服务端的实例，如果出现报错请参考以下“常见问题解答”部分。 

10、修改 Windows 系统本地 hosts 文件（C:\WINDOWS\system32\drivers\etc\hosts），在文件末尾加入以下虚拟站点配置： 
```
127.0.0.1 hush-app-frontend
127.0.0.1 hush-app-backend
```

11、重启 Xampp 的 Apache 和 MySQL，依次访问以下站点，确保可访问。 
```
Hush Framework 实例前台：http://hush-app-frontend
Hush Framework 实例后台：http://hush-app-backend
本书微博实例 API 调试后台：http://127.0.0.1:8001
本书微博实例 WEB 站点：http://127.0.0.1:8002 
```

12、使用 ipconfig 命令获取本机的局域网地址，比如 192.168.0.108（如果你用模拟器运行微博实例，也可以使用 Android 模拟器的映射地址 10.0.2.2），然后找到服务端源码中的 etc/app.config.php 文件，以及客户端源码 com.app.demos.base 包中的 C.java 文件，将 192.168.0.108 替换成本机的局域网地址（或者 10.0.2.2）。 

13、将客户端源码安装到 Android 模拟器上，即可运行！ 

## 特别说明（帮助理解源码）

**说明1：**如果遇到网络问题导致 Google 上的类库代码无法下载，可以使用 hush/cli sys uplib 尝试下载更新，如果还不成功就只能到 Hush Framework 项目网站的 Downloads 页面手动下载了。

**说明2：**如果本机已经安装过 Apache 和 MySQL 服务，应该注意以下几点： 

* 执行初始化命令“cli sys init”命令前，请修改 bin/cli.php 中的 MYSQL_USER 和 MYSQL_NAME 为本地 MySQL 的用户密码。

* 如果是 Hush Framework 无法导入数据库，请手动把 doc/sql/ 下面的两个 sql 文件导入到数据库中，然后再修改数据库配置文件即可（见下点）。

* 运行系统之前，请先修改 etc/database.mysql.php 配置文件中的 self::DEFAULT_USER 和 self::DEFAULT_PASS 为本地 MySQL 的用户密码。 

**说明3：**以下是服务端主要配置文件的说明，可能对理解框架有比较大的好处：

* app.config.php：源码配置的入口文件

* app.mapping.ini：URL路由配置文件，这里主要针对 Debug 后台

* database.mysql.php：数据库配置文件，可以在这里手动修改数据库的用户/密码

* global.datamap.php：协议配置文件，定义 M 方法来定制 JSON 协议的返回数据结构

* global.defines.php：全局配置文件，主要用于配置类库（基础/Hush类库）的路径

* global.message.php：国际化配置文件，定义 L 方法用于获取国际化文字

* global.session.php：会话配置文件，定义服务器 Session 会话的配置

## 常见问题解答（沙龙精选）

**问题1：**本书的客户端实例是基于Android哪个版本开发的，使用新版SDK是否会有问题？ 

考虑到向上的兼容性，本书所有客户端实例都是基于Android2.2来开发的。客户端实例源码的安装过程很简单，只需要把客户端实例源码导入到Eclipse中，然后在“项目属性”中设置Android的SDK版本为Android2.2版，即可运行。本书的实例均经过专门的测试，都是可以正常运行的；如果在实例源码安装过程中遇到问题，请到作者博客（ http://blog.csdn.net/shagoo ）进行提问。 

**问题2：**我是Android的初学者，可以些学习的建议吗？ 

从Androider的角度，建议按照以下步骤学习本书： 

* 阅读《第2章.Android开发准备》学习Android开发基础精要。 

* 阅读4、5两章了解本书微博实例的项目架构和规划。 

* 阅读《第3章.PHP开发准备》学习PHP开发基础精要。 

* 阅读《第6章.服务端开发》学习使用PHP开发服务端接口。 

* 阅读《第7章.客户端开发》学习如何结合PHP服务器API开发移动互联应用。 

* 阅读8到10章，学习服务端和客户端的压力测试和优化方法。 

* 阅读剩下的进阶章节学习Android特色功能开发、游戏开发以及其他扩展知识。 

本书不仅能让你学到Android客户端开发的技巧精要，还可以学到流行脚本语言PHP的用法以及服务端开发的知识，准备篇、实战篇、优化篇、进阶篇的内容层层递进、由浅入深，非常适合初学者进行系统的学习。另外，本书的内容也十分丰富，可以当作参考手册来使用。 

**问题3：**如果只会一点PHP，不会Android的开发，如何同时学好PHP和Android？ 

 从PHPer的角度，建议按照以下步骤学习本书：

* 阅读《第3章.PHP开发准备》学习PHP开发基础精要。

* 阅读4、5两章，了解本书微博实例的项目架构和规划。

* 阅读《第6章.服务端开发》学习使用PHP开发服务端接口。

* 阅读《第2章.Android开发准备》学习Android开发基础精要。

* 阅读《第7章.客户端开发》学习如何结合PHP服务器API开发移动互联应用。

* 阅读8到10章，学习服务端和客户端的压力测试和优化方法。

* 阅读剩下的进阶章节学习Android特色功能开发、游戏开发以及其他扩展知识。 

PHP和Android（Java）毕竟是两种不同的语言，学习的时候注意多类比，多思考；相信对你的编程之路会很有帮助。就本书的内容比重而言，Android和PHP各占一半左右，比较合适对移动互联网有兴趣的读者来拓宽思路，当然如果你对作架构有兴趣的话，本书也是不错的选择。 

**问题4：**应用服务端开发和常见WEB站点的开发有什么不同？ 

应用服务端开发确实和开发普通的WEB站点不大一样。最直观的不同点就是应用服务端都是API形式的，而web站点是有界面的。从调试的角度来看，API的调试会更加麻烦一些，恰好本书的PHP服务端框架提供了一套很方便的调试框架，有兴趣的话可以多研究一下~ 

**问题5：**运行初始化命令（hush/cli sys init）后提示数据库导入失败如何解决？ 

* 错误提示1：提示 Can't connect to MySQL server on '127.0.0.1' 

* 解决方法1：原因是本地 MySQL 没有启动。请确认一下 Xampp 的 MySQL 服务是否已启动。 

* 错误提示2：提示 Access denied for user 'root'@'localhost' 

* 解决方法2：原因是MySQL密码不对。如果不想改变原有密码，可以修改数据库配置文件 etc/database.mysql.php，将文件中的 self::DEFAULT_PASS 修改成现有的 root 密码；或者直接在 phpMyAdmin 中将 root 密码改为 passwd。 

**问题6：**微博客户端为何无法连接服务器（提示“网络错误”）？ 

首先，请确认本机的微博服务端是否已经启动，即 http://127.0.0.1:8001 是否可以访问；如果可以，请再确认本机的局域网地址是什么（Windows 下可使用 ipconfig 命令获取，比如：192.168.1.28；或者直接使用 Android 模拟器的映射地址 10.0.2.2），然后，找到客户端源码 com.app.demos.base 包中的 C.java 文件，将 192.168.1.2 替换成本机的局域网地址（或者 10.0.2.2），重新安装运行微博客户端即可。此外，最好把服务端代码中的 etc/app.config.php 中的 HOST_SERVER 和 HOST_WEBSITE 常量也替换掉，这样不至于出现其他问题。 

**问题7：**服务端代码报错提示：Strict Standards: Non-static method MysqlConfig?::getInstance() should not be called statically ...？ 

找到etc/global.defines.php的error_reporting设置，修改为：error_reporting(E_ERROR | E_WARNING);

**问题8：**为何运行“cli doc build”提示报错，无法生成文档？ 

这是书本代码使用的 Hush Framework 版本较老的问题（新版本 Hush Framework 没有此问题），可以到官网的 Downloads 页面下载微博实例源码修正包（weibo-patch-20130502.zip），解压并覆盖到原来的源码目录（android-php-weibo）下即可。 
