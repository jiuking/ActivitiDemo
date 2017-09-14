package org.activiti.designer.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	public static void post(String url,String encode) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		// List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// formparams.add(new BasicNameValuePair("type", "house"));
		UrlEncodedFormEntity uefEntity;
		try {
			// uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			// httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					System.out.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void get(String url,String encode) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: " + EntityUtils.toString(entity));
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 
     * 发送 http put 请求，参数以原生字符串进行提交 
     * @param url 
     * @param encode 
     * @return 
     */  
    public static void put(String url,String encode){  
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();   
    	encode = "UTF-8";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();   
        HttpPut httpput = new HttpPut(url);

        //设置header
/*        httpput.setHeader("Content-type", "application/json");    
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpput.setHeader(entry.getKey(),entry.getValue());
            }
        }
*/        //组织请求参数  
//        StringEntity stringEntity = new StringEntity(stringJson, encode);  
//        httpput.setEntity(stringEntity);  
        String content = null;  
        CloseableHttpResponse  httpResponse = null;  
        try {  
            //响应信息
            httpResponse = closeableHttpClient.execute(httpput);  
            HttpEntity entity = httpResponse.getEntity();  
            content = EntityUtils.toString(entity, encode);  
//            response.setBody(content);
//            response.setHeaders(httpResponse.getAllHeaders());
//            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
//            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            System.out.println(content+"---"+httpResponse.getAllHeaders());
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                httpResponse.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
            closeableHttpClient.close();  //关闭连接、释放资源  
        } catch (IOException e) {  
            e.printStackTrace();  
        }    
    }

}
