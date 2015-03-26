package com.registerservic.app.utils;

/**
 * Created by Artyom on 3/20/2015.
 */
public class Utils {

    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    public static boolean isOnline(){
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p.waitFor();
            return (returnVal == 0);
        }catch(Exception ex){

        }
        return false;
    }
}
