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
		// ����Ĭ�ϵ�httpClientʵ��.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// ����httppost
		HttpPost httppost = new HttpPost(url);
		// ������������
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
			// �ر�����,�ͷ���Դ
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
			// ����httpget.
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());
			// ִ��get����.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// ��ȡ��Ӧʵ��
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// ��ӡ��Ӧ״̬
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// ��ӡ��Ӧ���ݳ���
					System.out.println("Response content length: " + entity.getContentLength());
					// ��ӡ��Ӧ����
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
			// �ر�����,�ͷ���Դ
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 
     * ���� http put ���󣬲�����ԭ���ַ��������ύ 
     * @param url 
     * @param encode 
     * @return 
     */  
    public static void put(String url,String encode){  
        //HttpClients.createDefault()�ȼ��� HttpClientBuilder.create().build();   
    	encode = "UTF-8";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();   
        HttpPut httpput = new HttpPut(url);

        //����header
/*        httpput.setHeader("Content-type", "application/json");    
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpput.setHeader(entry.getKey(),entry.getValue());
            }
        }
*/        //��֯�������  
//        StringEntity stringEntity = new StringEntity(stringJson, encode);  
//        httpput.setEntity(stringEntity);  
        String content = null;  
        CloseableHttpResponse  httpResponse = null;  
        try {  
            //��Ӧ��Ϣ
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
            closeableHttpClient.close();  //�ر����ӡ��ͷ���Դ  
        } catch (IOException e) {  
            e.printStackTrace();  
        }    
    }

}
