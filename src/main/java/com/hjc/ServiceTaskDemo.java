package com.hjc;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTaskDemo implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("variavles=" + execution.getVariables());  
        execution.setVariable("result", "false");  
	}
}
