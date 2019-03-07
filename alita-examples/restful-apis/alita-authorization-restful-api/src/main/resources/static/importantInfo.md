# 帐号密码登录接口

>  通过用户名密码登录,登录成功后返回token

接口地址: /oauth/token

请求方式: POST

请求参数:

* username  用户名
* password  用户密码
* grant_type: password  固定参数
* scope:select 固定参数
* client_id: 根据客户端不同使用不同固定参数
* client_secret: 根据客户端不同使用不同固定参数

返回:

* access_token: 请求token,之后请求接口使用,有效期默认2小时
* refresh_token: 刷新token,有效期默认30天. 由于access_token 失效比较短(默认2小时),当 access_token 过期后,可以使用 refresh_token 重新获取一个 access_token ,而不用让用户输入帐号密码
* token_type, expires_in, scope, jti 客户端用不到,可以忽略
* 其他参数为附加参数,客户端一般用不到

# 刷新token接口

> 当 access_token 失效后,可以通过该接口重新获得一个 access_token ,避免再次让用户输入帐号密码

接口地址: /oauth/token

请求方式: POST

请求参数:

* grant_type: refresh_token  固定值
* client_id: 参考登录接口
* client_secret: 参考登录接口
* refresh_token: 登录接口返回的 refresh_token

返回: 同登录接口,其中 refresh_token 是重新生成并以刷新时间为开始时间重新延时有效期(旧的refresh_token在有效期内依旧生效)

# 检查token接口

>  该接口默认是关闭的,因为目前使用JWT作为token,每个服务都可以自行校验

* 接口地址: /oauth/check_token 
* 请求方式: POST
* 参数: token