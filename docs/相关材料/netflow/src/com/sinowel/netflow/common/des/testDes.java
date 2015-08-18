package com.sinowel.netflow.common.des;


public class testDes {
	public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub   
        String key = "20150422";  
        String text = "oUZWKs3idWeRx0knwttyqHlDQmSk";  
        String result1 = Des.encryptDES(text,key);  
        String result2 = Des.decryptDES(result1, key);  
        System.out.println(result1);  
        System.out.println(result2);
    }  

}
