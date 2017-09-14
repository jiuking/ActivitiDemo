package org.activiti.designer.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class UserProcessTest {
	
	private String filename = "C:\\Users\\Administrator\\git\\ActivitiDemo\\src\\main\\resources\\Activiti\\activitiDemo.bpmn";
	InputStream file = ClassLoader.getSystemClassLoader().getResourceAsStream("\\Activiti\\activitiDemo.bpmn");

	//流程引擎对象  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
	@Test
	public void startProcess() throws Exception {
		Deployment deployment = processEngine.getRepositoryService()//  
                .createDeployment()//创建部署对象  
                .addInputStream("myProcess.bpmn20.xml", file)//部署加载资源文件  
                .deploy();  
//		System.out.println("部署ID："+deployment.getId()); 
		//2.启动流程  
        ProcessInstance pi = processEngine.getRuntimeService()//  
                .startProcessInstanceByKey("myProcess");  
        System.out.println("pid:" + pi.getId());  
	}
	
	/**查询我的个人任务,没有执行结果*/  
    @Test  
    public void findPersonalTaskList() throws Exception{  
        // 任务办理人  
    	startProcess();
    	
        String assignee = "小A";  
        List<Task> list = processEngine.getTaskService()//  
                .createTaskQuery()//  
                .taskCandidateUser(assignee)// 个人任务的查询  
                .list();  
        
        if (list != null && list.size() > 0) {  
            for (Task task : list) {  
                System.out.println("任务ID：" + task.getId());  
                System.out.println("任务的办理人：" + task.getAssignee());  
                System.out.println("任务名称：" + task.getName());  
                System.out.println("任务的创建时间：" + task.getCreateTime());  
                System.out.println("流程实例ID：" + task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
        TaskService task = processEngine.getTaskService();
        Task ts = task.createTaskQuery().singleResult();
		System.out.println(ts.getName()+"==="+ts.getId()+"代办人："+ts.getAssignee()+"候选人"+ts.getProcessInstanceId());
		task.complete(ts.getId());
		
		List<Task> list1 = processEngine.getTaskService()//  
                .createTaskQuery()//  
                .taskAssignee(assignee)// 个人任务的查询  
                .list();  
        
        if (list1 != null && list1.size() > 0) {  
            for (Task task1 : list1) {  
                System.out.println("任务ID：" + task1.getId());  
                System.out.println("任务的办理人：" + task1.getAssignee());  
                System.out.println("任务名称：" + task1.getName());  
                System.out.println("任务的创建时间：" + task1.getCreateTime());  
                System.out.println("流程实例ID：" + task1.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
		
    }  
    
    @Test  
    public void findGroupCandidate() throws Exception{  
    	System.out.println("asdf");
    	// 任务办理人  
    	startProcess();
        //任务ID  
        String taskId = "9";  
        List<IdentityLink> list = processEngine.getTaskService()//  
                        .getIdentityLinksForTask(taskId);  
  
        if(list!=null && list.size()>0){  
            for(IdentityLink identityLink:list){  
                System.out.println("任务ID："+identityLink.getTaskId());  
                System.out.println("流程实例ID："+identityLink.getProcessInstanceId());  
                System.out.println("用户ID："+identityLink.getUserId());  
                System.out.println("工作流角色ID："+identityLink.getGroupId());  
                System.out.println("#########################################");  
            }  
        }  
    }  
    /**查询历史的组任务列表
     * @throws Exception */  
    @Test  
    public void findHistoryGroupCandidate() throws Exception{  
    	findPersonalTaskList();
    	System.out.println("历史的组");
        //流程实例ID  
        String processInstanceId = "5";  
        List<HistoricIdentityLink> list = processEngine.getHistoryService()  
                        .getHistoricIdentityLinksForProcessInstance(processInstanceId);  
        if(list!=null && list.size()>0){  
            for(HistoricIdentityLink identityLink:list){  
                System.out.println("任务ID："+identityLink.getTaskId());  
                System.out.println("流程实例ID："+identityLink.getProcessInstanceId());  
                System.out.println("用户ID："+identityLink.getUserId());  
                System.out.println("工作流角色ID："+identityLink.getGroupId());  
                System.out.println("#########################################");  
            }  
        }  
    }     
}
