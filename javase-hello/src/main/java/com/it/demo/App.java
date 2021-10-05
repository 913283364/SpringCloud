package com.it.demo;

import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //用UUID工具类生成用不重复的字符串做数据表主键id
        String string = UUID.randomUUID().toString();
        System.out.println(string);
        //2603abd5-5c77-4cf1-a8d9-4cf0c13aa568

        //用replace删减去字符串的"-"得到32为字符串
        string=string.replace("-","");
        System.out.println(string.length());
        //b17f13f26d8b46e6a5a3fe1d78863405
    }


}


