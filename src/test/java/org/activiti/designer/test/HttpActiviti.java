package org.activiti.designer.test;

import org.junit.Test;

public class HttpActiviti {
	@Test
	public void testHttp(){
		HttpClientUtil.get("http://192.168.7.249:8080/zhph_loanActivity/process/approv", "");
	}
	@Test
	public void testHttpPut(){
		HttpClientUtil.put("http://192.168.7.249:8080/zhph_loanActivity/process/approv", "");
	}
	@Test
	public void testHttpPost(){
		HttpClientUtil.post("http://192.168.7.249:8080/zhph_loanActivity/process/task/anyone/1155079", "");
	}
}
