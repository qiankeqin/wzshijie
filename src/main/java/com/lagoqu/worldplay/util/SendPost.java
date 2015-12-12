package com.lagoqu.worldplay.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

public class SendPost {

	
	private static TrustManager MyX509TrustManager = new X509TrustManager() { 

		@Override 
	    public X509Certificate[] getAcceptedIssuers() { 
	        return null; 
	    } 

	    @Override 
	    public void checkServerTrusted(X509Certificate[] chain, String authType) 
	    throws CertificateException { 
	    } 

	    @Override 
	    public void checkClientTrusted(X509Certificate[] chain, String authType) 
	    throws CertificateException { 
	    }
	};

	public JSONObject postHuanxin(String url,JSONObject jsonObject) {
		JSONObject ojb = null;
		try {
		
		 //设置SSLContext 
        SSLContext sslcontext = SSLContext.getInstance("TLS"); 
        sslcontext.init(null, new TrustManager[]{MyX509TrustManager}, null);
        //打开连接
        URL reqURL = new URL(url); //创建URL对象
		HttpsURLConnection httpsConn = (HttpsURLConnection)reqURL.openConnection();
		
		//设置套接工厂 
        httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());
		
        //加入数据 
        httpsConn.setDoOutput(true);
        httpsConn.setDoInput(true);
        httpsConn.setRequestMethod("POST"); 
        httpsConn.setDoOutput(true); 
        httpsConn.setUseCaches(false);
        httpsConn.setInstanceFollowRedirects(true);
        httpsConn.setRequestProperty("Content-Type","application/json");
//      httpsConn.setRequestProperty("Authorization","Bearer YWMtky1cDBDaEeW3hZ3Ki5BWHgAAAU8b0Bao45wiiyAUFC87DS_U9lemyMJR1vo");
        httpsConn.connect();
        
        DataOutputStream out = new DataOutputStream( 
                httpsConn.getOutputStream()); 
       

        out.writeBytes(jsonObject.toString());
        out.flush(); 
        out.close();
		//取得该连接的输入流，以读取响应内容 
        //读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()));
        int code = httpsConn.getResponseCode(); 
	        if (HttpsURLConnection.HTTP_OK == code){ 
	        String lines;
	        StringBuffer sb = new StringBuffer("");
		        while ((lines = reader.readLine()) != null) {
		            lines = new String(lines.getBytes(), "utf-8");
		            sb.append(lines);
		             }  
	        ojb=JSONObject.fromObject(sb.toString());
	        }else{
	        	ojb.element("code", code);
	        	return ojb;
	        }
	        
		 } catch (KeyManagementException e) { 
	            e.printStackTrace(); 
	        } catch (NoSuchAlgorithmException e) { 
	            e.printStackTrace(); 
	        } catch (MalformedURLException e) { 
	            e.printStackTrace(); 
	        } catch (ProtocolException e) { 
	            e.printStackTrace(); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        }
		return ojb;
		

    }    
}
