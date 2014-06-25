应用部署

1、把 src/com.app.XXX.conf 里面 C.java 里面的 api 和 web config 里面的 IP 改成你机器的 IP
2、发布 app-XXX-client 应用即可

目录说明

src/com.app.XXX.app     : 控制器类（Controller）
src/com.app.XXX.auth    : 身份验证基类
src/com.app.XXX.base    : 框架核心基类
src/com.app.XXX.conf    : 应用配置基类（静态）
src/com.app.XXX.demo    : 示例类（Demo）
src/com.app.XXX.model   : 模型类（Model）
src/com.app.XXX.popup   : 弹出框类
src/com.app.XXX.util    : 工具类库

gen/com.app.XXX         : 资源配置基类（静态/自动生成）

res/drawable            : 图片目录
res/layout              : 布局配置（View）
res/menu                : 菜单配置（View）
res/values              : 字符配置

doc/                    : 文档目录