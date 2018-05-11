package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {

    public static void main(String[] args) {
        String text = "https://m.tbdress.com/list/cheap-women-dresses-100507/zfdhbsdfhg-fgsgfd/";

        Pattern p = Pattern.compile("/list/cheap-(.*?)-(\\\\d+)/");
        //Pattern p = Pattern.compile("list/cheap(-.*?)-\\d+/");
        Matcher matcher = p.matcher(text);

        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
//            for (int i = 0;i<matcher.groupCount();i++){
//                System.out.println(matcher.group(i));
//            }
        }


    }
}
