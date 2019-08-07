package com.jypay.test.mytest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.RuntimeErrorException;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;



public class CoinBaseTest {

	static String baseUrl = "https://api.coinbase.com";
	
	static String apiKey = "xxxxx";
	
	static String secretKey="xxxxxxxx";
	
	
	private String url;
	
	private String method;
	
	
	private String body;
	
	@Before
	public void init(){
		body="";
	}
	
	@Test
	public void user(){
		
        url="/v2/user";
		
		method="GET";
		
		 JSONObject jsonParam = new JSONObject();
		 
		if(jsonParam.size()>0){
			body=jsonParam.toString();
		}	
	}
	
	
	@Test
	public void accounts(){
		
        url="/v2/accounts/c130ebdd-c7fe-5df2-b302-a6d03f35c9a7";
		
		method="GET";
		
		 JSONObject jsonParam = new JSONObject();
		 
		if(jsonParam.size()>0){
			body=jsonParam.toString();
		}	
	}
	
	
	
	@Test
	public void addresses(){
		
        url="/v2/accounts/c130ebdd-c7fe-5df2-b302-a6d03f35c9a7/addresses";
		
		method="GET";
		
		 JSONObject jsonParam = new JSONObject();
		 
		if(jsonParam.size()>0){
			body=jsonParam.toString();
		}	
	}
	
	
	@Test
	public void createaddresses(){
		
        url="/v2/accounts/c130ebdd-c7fe-5df2-b302-a6d03f35c9a7/addresses";
		
		method="POST";
		
		 JSONObject jsonParam = new JSONObject();
		 jsonParam.put("name","New receive address");
		if(jsonParam.size()>0){
			body=jsonParam.toString();
		}	
	}
	
	
	
	@After
	public  void after() throws Exception {
		
		Map<String, String> headers = new HashMap<String, String>();
		 
        String timestamp = Instant.now().getEpochSecond() + "";
        
        headers.put("CB-ACCESS-KEY", apiKey);
        headers.put("CB-ACCESS-SIGN", HMACSHA256(url, method, body, timestamp));
        headers.put("CB-ACCESS-TIMESTAMP", timestamp);
        headers.put("CB-VERSION", "2019-06-15");
        String result =null;
        
        if(StringUtils.equals(method, "GET"))
		    result = HttpClientUtils.doGet(baseUrl+url, headers);
        else
        	result = HttpClientUtils.doPost(baseUrl+url, headers,body);
        
		System.out.println(result);
	}
	
	
	
	
	private String HMACSHA256(String requestPath, String method, String body, String timestamp) {
    	
	    String prehash = timestamp + method.toUpperCase() + requestPath + body;
	    	
	    try { 
	        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

	        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");

	        sha256_HMAC.init(secret_key);

	        byte[] array = sha256_HMAC.doFinal(prehash.getBytes("UTF-8"));
    
	        StringBuffer sb = new StringBuffer();  
	        for(int i = 0; i < array.length; i++) {  
	            String hex = Integer.toHexString(array[i] & 0xFF);  
	            if(hex.length() < 2){  
	                sb.append(0);  
	            }  
	            sb.append(hex);  
	        }  
       
		     return sb.toString();
		        
	        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
	           
	            throw new RuntimeErrorException(new Error("Cannot set up authentication headers."));
	       }
	    }
}
