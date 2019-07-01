package com.liutaiyue.oauth2.test;

import org.bson.internal.Base64;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/1 11:31
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
public class Demotest {
    public static void main(String[] args) {
        String clinetString = "XcWebApp"+":"+"XcWebApp";
        String encode = Base64.encode(clinetString.getBytes());
    }
}
