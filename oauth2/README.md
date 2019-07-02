认证服务系统  
此系统具有两种认证模式：1，password 2，code
第三方认证使用code模式，用户登录使用password模式。
使用code模式的方式：
1，申请授权码
get localhost:40400/auth/oauth/authorize?
client_id=XcWebApp&response_type=code&scop=app&redirect_uri=http://localhost:8080
参数列表如下：
client_id：客户端id，和授权配置类中设置的客户端id一致。
response_type：授权码模式固定为code
scop：客户端范围，和授权配置类中设置的scop一致。
redirect_uri：跳转uri，当授权码申请成功后会跳转到此地址，并在后边带上code参数（授权码）。

跳转到登录页面，输入client的账号密码 （默认oauth_client_details表里的数据，此系统账号XcWebApp，密码XcWebApp ）。
重新定向到http://localhost:8080，会拼接一个code
2，申请令牌
拿到授权码后，申请令牌。
post  http://localhost:40400/auth/oauth/token
参数如下：
grant_type：授权类型，填写authorization_code，表示授权码模式
code：授权码，就是刚刚获取的授权码，注意：授权码只使用一次就无效了，需要重新申请。
redirect_uri：申请授权码时的跳转url，一定和申请授权码时用的redirect_uri一致。
返回的access_token作为访问其他系统的bearer token传值
使用password模式的方式：
post http://localhost:40400/auth/oauth/token
参数：
grant_type：密码模式授权填写password
username：账号
password：密码
并且此链接需要使用 http Basic认证。