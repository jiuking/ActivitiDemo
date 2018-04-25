package org.activiti.designer.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class CopyOfFQZTest {

	//流程引擎对象  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    
    private String filename = "E:\\ActivitiDemo\\src\\main\\resources\\Activiti\\MyProcessTest.bpmn";
      
    /**部署流程定义+启动流程实例*/  
    @Test  
    public void deployementAndStartProcess(){  
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("myProcess.bpmn20.xml");  
//        InputStream inputStreampng = this.getClass().getResourceAsStream("task.png");  
        //部署流程定义  
        Deployment deployment;
		try {
			deployment = processEngine.getRepositoryService()//  
			                    .createDeployment()//创建部署对象  
			                    .addInputStream("myProcess.bpmn20.xml",new FileInputStream(filename))//部署加载资源文件  
//                            .addInputStream("task.png", inputStreampng)//  
			                    .deploy();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
//        System.out.println("部署ID："+deployment.getId());  
        //启动流程实例  
          
        ProcessInstance pi = processEngine.getRuntimeService()//  
                            .startProcessInstanceByKey("myProcess");//使用流程定义的key的最新版本启动流程  
        System.out.println("流程实例ID："+pi.getId());  
        System.out.println("流程定义的ID："+pi.getProcessDefinitionId());  
    }  
      
    /**完成任务*/  
    @Test  
    public void completeTask(){  
        //任务ID    ${fristResult==091000}
        String taskId = "7508";  
        Map<String,Object> map = new  HashMap<String,Object>();
//        map.put("end", -1);
        map.put("lastEnd", 1);
        processEngine.getTaskService()//  
                        .complete(taskId,map);  
        System.out.println("完成任务："+taskId);  
    }  
      
}