package com.liutaiyue.oauth2.test;

import com.liutaiyue.common.client.XcServiceList;
import org.bson.internal.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/1 10:49  //申请令牌测试
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClient {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testClient(){
        //采用客户端负载均衡，从eureka获取服务ip和端口
        ServiceInstance choose = loadBalancerClient.choose(XcServiceList.SERVICE_OAUTH2);
        URI uri = choose.getUri();
        String auth_url = uri + "/auth/oauth/token";
        /**头部是Authorization basic 模式 将账号密码通过username:password 通过base64加密*/
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String httpBasic = httpBasic("XcWebApp", "XcWebApp");
        headers.add("Authorization",httpBasic);
        /**body 密码模式*/
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username","itcast");
        body.add("password","123");

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
        Map body1 = exchange.getBody();
        System.out.println(body1);


    }

    /**
     * basic base64
     * @param clientId
     * @param clinetPwd
     * @return
     */
    private String httpBasic(String clientId,String clinetPwd){
        String clinetString = clientId + ":" + clinetPwd;
        return "Basic "+Base64.encode(clinetString.getBytes());
    }

}
