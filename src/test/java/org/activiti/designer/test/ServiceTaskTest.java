package org.activiti.designer.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ServiceTaskTest {

	//流程引擎对象  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    
    private String filename = "E:\\ActivitiDemo\\src\\main\\resources\\Activiti\\ServiceTaskTest.bpmn";
      
    /**部署流程定义+启动流程实例*/  
    @Test  
    public void deployementAndStartProcess(){  
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("myProcess.bpmn20.xml");  
        //部署流程定义  
        Deployment deployment;
		try {
			deployment = processEngine.getRepositoryService()//  
			                    .createDeployment()//创建部署对象  
			                    .addInputStream("myProcess.bpmn20.xml",new FileInputStream(filename))//部署加载资源文件  
			                    .deploy();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  

    @Test
    public void startProcess(){
    	ProcessInstance pi = processEngine.getRuntimeService()//  
                .startProcessInstanceByKey("myProcess");//使用流程定义的key的最新版本启动流程  
    	System.out.println("流程实例ID："+pi.getId());  
    	System.out.println("流程定义的ID："+pi.getProcessDefinitionId());  
    }
    
    /**完成任务  案件分配--》案件调查员*/  
    @Test  
    public void completeTask(){  
        //任务ID    ${fristResult==091000}
        String taskId = "17508";  
        Map<String,Object> map = new  HashMap<String,Object>();
        map.put("caseSignNo", "11");
        map.put("searchManager","张三");
//        map.put("lastResult", "0000");
//        map.put("fristResult", "09000");
//        map.put("CaseEmpExist", "093000");
        
        processEngine.getTaskService()//  
                        .complete(taskId,map);  
        System.out.println("完成任务："+taskId);  
    }  
    
}
