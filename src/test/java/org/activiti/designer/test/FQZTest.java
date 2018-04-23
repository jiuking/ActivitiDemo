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

public class FQZTest {

	//流程引擎对象  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    
    private String filename = "E:\\workspace\\ActivitiDemo\\src\\main\\resources\\Activiti\\MyProcessTest.bpmn";
      
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
      
    /**查询我的个人任务*/  
    @Test  
    public void findPersonalTaskList(){  
//    	deployementAndStartProcess();
//    	claim();
        //任务办理人  
        String assignee = "唐僧";  
        List<Task> list = processEngine.getTaskService()//  
                        .createTaskQuery()//  
                        .taskAssignee(assignee)//个人任务的查询  
                        .list();  
        if(list!=null && list.size()>0){  
            for(Task task:list){  
                System.out.println("任务ID："+task.getId());  
                System.out.println("任务的办理人："+task.getAssignee());  
                System.out.println("任务名称："+task.getName());  
                System.out.println("任务的创建时间："+task.getCreateTime());  
                System.out.println("流程实例ID："+task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
    }  
      
    /**查询组任务*/  
    @Test  
    public void findGroupTaskList(){  
        //任务办理人  
    	deployementAndStartProcess();
        String candidateUser = "猪八戒";  
        List<Task> list = processEngine.getTaskService()//  
                        .createTaskQuery()//  
                        .taskCandidateUser(candidateUser)//参与者，组任务查询  
                        .list();  
        if(list!=null && list.size()>0){  
            for(Task task:list){  
                System.out.println("任务ID："+task.getId());  
                System.out.println("任务的办理人："+task.getAssignee());  
                System.out.println("任务名称："+task.getName());  
                System.out.println("任务的创建时间："+task.getCreateTime());  
                System.out.println("流程实例ID："+task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
    }  
      
    /**完成任务*/  
    @Test  
    public void completeTask(){  
        //任务ID  
    	
    	Map<String,Object> map = new HashMap<String, Object>();
        String taskId = "12508";  
        map.put("testInteger", 1);
        map.put("end", 1);
        processEngine.getTaskService()//  
                        .complete(taskId,map);  
        System.out.println("完成任务："+taskId);  
    }  
    
    @Test
    public void completeSecondTask(){
    	Map<String,Object> map = new HashMap<String, Object>();
        String taskId = "15005";  
        map.put("asdd", 1);
        map.put("end", 1);
        processEngine.getTaskService()//  
                        .complete(taskId,map);  
        System.out.println("完成任务："+taskId);  
    }
      
    /**查询正在执行的组任务列表*/  
    @Test  
    public void findGroupCandidate(){  
        //任务ID  
        String taskId = "5909";  
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
      
    /**查询历史的组任务列表*/  
    @Test  
    public void findHistoryGroupCandidate(){  
        //流程实例ID  
        String processInstanceId = "5905";  
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
      
    /**将组任务指定个人任务(拾取任务)*/  
    @Test  
    public void claim(){  
//    	deployementAndStartProcess();
        //任务ID  
        String taskId = "10008";  
        //个人任务的办理人  
        String userId = "唐僧";  
        processEngine.getTaskService()//  
                    .claim(taskId, userId);  
    }  
      
    /**将个人任务再回退到组任务（前提：之前这个任务是组任务）*/  
    @Test  
    public void setAssignee(){  
        //任务ID  
        String taskId = "6308";  
        processEngine.getTaskService()//  
                        .setAssignee(taskId, null);  
    }  
      
    /**向组任务中添加成员*/  
    @Test  
    public void addGroupUser(){  
        //任务ID  
        String taskId = "6308";  
        //新增组任务的成员  
        String userId = "如来";  
        processEngine.getTaskService()//  
                    .addCandidateUser(taskId, userId);  
    }  
      
    /**向组任务中删除成员*/  
    @Test  
    public void deleteGroupUser(){  
        //任务ID  
        String taskId = "6308";  
        //新增组任务的成员  
        String userId = "猪八戒";  
        processEngine.getTaskService()//  
                    .deleteCandidateUser(taskId, userId);  
    }  
    
}