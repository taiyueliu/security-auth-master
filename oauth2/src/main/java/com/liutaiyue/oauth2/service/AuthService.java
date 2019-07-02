package com.liutaiyue.oauth2.service;

import com.alibaba.fastjson.JSON;
import com.liutaiyue.common.client.XcServiceList;
import com.liutaiyue.common.domain.ucenter.ext.AuthToken;
import com.liutaiyue.common.domain.ucenter.response.AuthCode;
import com.liutaiyue.common.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.Base64Utils;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/1 13:58
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@Service
public class AuthService {
    @Value("${auth.tokenValiditySeconds}")
    private Integer tokenValiditySeconds;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final static String USER_TOKEN = "user_token:";


    public AuthToken login(String clientId, String clientSecret, String username, String password) {
        //请求spring security申请令牌
        AuthToken authToken = this.applyToken(username, password, clientId, clientSecret);
        if(authToken == null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        //用户身份令牌
        String access_token = authToken.getAccess_token();
        //存储到redis中的内容
        String jsonString = JSON.toJSONString(authToken);
        //将令牌存储到redis
        boolean result = this.saveToken(access_token, jsonString, tokenValiditySeconds);
        if (!result) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        return authToken;
    }

    //申请令牌
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        //采用客户端负载均衡，从eureka获取服务ip和端口
        ServiceInstance choose = loadBalancerClient.choose(XcServiceList.SERVICE_OAUTH2);
        URI uri = choose.getUri();
        String auth_url = uri + "/auth/oauth/token";
        /**头部是Authorization basic 模式 将账号密码通过username:password 通过base64加密*/
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String httpBasic = httpBasic(clientId, clientSecret);
        headers.add("Authorization",httpBasic);
        /**body 密码模式*/
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);

        /**
         * /auth/oauth/token 需要header和body
         * @Nullable T body, @Nullable MultiValueMap<String, String> headers
         */
        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<>(body, headers);
        //指定 restTemplate当遇到400或401响应时候也不要抛出异常，也要正常返回值
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        //远程调用申请令牌 URI var1, HttpMethod var2, @Nullable HttpEntity<?> var3, Class<T> var4
        ResponseEntity<Map> exchange = restTemplate.exchange(auth_url, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        Map bodyMap = exchange.getBody();
        if(bodyMap == null ||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null){

            //解析spring security返回的错误信息
            if(bodyMap!=null && bodyMap.get("error_description")!=null){
                String error_description = (String) bodyMap.get("error_description");
                if(error_description.indexOf("UserDetailsService returned null")>=0){
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                }else if(error_description.indexOf("坏的凭证")>=0){
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }
            return null;
        }

        AuthToken authToken = new AuthToken();
        authToken.setAccess_token((String) bodyMap.get("jti"));//用户身份令牌
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));//刷新令牌
        authToken.setJwt_token((String) bodyMap.get("access_token"));//jwt令牌
        return authToken;
    }


    //从redis查询令牌
    public AuthToken getUserToken(String token){
        String key = USER_TOKEN + token;
        //从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(key);
        //转成对象
        try {
            AuthToken authToken = JSON.parseObject(value, AuthToken.class);
            return authToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     *
     * @param access_token 用户身份令牌
     * @param content  内容就是AuthToken对象的内容
     * @param ttl 过期时间
     * @return
     */
    private boolean saveToken(String access_token,String content,long ttl){
        String key = USER_TOKEN + access_token;
        stringRedisTemplate.boundValueOps(key).set(content,ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire>0;
    }
    //删除token
    public boolean delToken(String access_token){
        String key = USER_TOKEN + access_token;
        stringRedisTemplate.delete(key);
        return true;
    }

    /**
     * basic base64
     * @param clientId
     * @param clinetPwd
     * @return
     */
    private String httpBasic(String clientId,String clinetPwd){
        String clinetString = clientId + ":" + clinetPwd;
        byte[] encode = Base64Utils.encode(clinetString.getBytes());
        return "Basic "+ new String(encode);
    }
}
