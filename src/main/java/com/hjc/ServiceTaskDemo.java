package com.hjc;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTaskDemo implements JavaDelegate {
	
	private Expression result;
	
	private Expression text;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("result", "false");  
        System.out.println(result.getValue(execution));
        System.out.println(text.getValue(execution));
        System.out.println("variavles=" + execution.getVariables()); 
	}
}
