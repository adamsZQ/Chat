package com.chat.android.util;

/**
 * Created by 26241 on 2017/3/4.
 */

public class Address {
    final public static String Address = "http://xiao2.8wss.com/irobot/ask?uid=1&nickname=123&sex=male&location=⿊⻰江哈尔滨市×tamp=1923842423&question=" ;
//    final public static String Address = "http://www.tuling123.com/openapi/api?key=fe1a9a0ed7be42aba7a192e3164044a4&info=";
    public static String setAddress(String address){
        return Address+address;
    }
}
