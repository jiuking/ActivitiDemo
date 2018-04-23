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

	//�����������  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    
    private String filename = "E:\\workspace\\ActivitiDemo\\src\\main\\resources\\Activiti\\MyProcessTest.bpmn";
      
    /**�������̶���+��������ʵ��*/  
    @Test  
    public void deployementAndStartProcess(){  
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("myProcess.bpmn20.xml");  
//        InputStream inputStreampng = this.getClass().getResourceAsStream("task.png");  
        //�������̶���  
        Deployment deployment;
		try {
			deployment = processEngine.getRepositoryService()//  
			                    .createDeployment()//�����������  
			                    .addInputStream("myProcess.bpmn20.xml",new FileInputStream(filename))//���������Դ�ļ�  
//                            .addInputStream("task.png", inputStreampng)//  
			                    .deploy();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
//        System.out.println("����ID��"+deployment.getId());  
        //��������ʵ��  
          
        ProcessInstance pi = processEngine.getRuntimeService()//  
                            .startProcessInstanceByKey("myProcess");//ʹ�����̶����key�����°汾��������  
        System.out.println("����ʵ��ID��"+pi.getId());  
        System.out.println("���̶����ID��"+pi.getProcessDefinitionId());  
    }  
      
    /**��ѯ�ҵĸ�������*/  
    @Test  
    public void findPersonalTaskList(){  
//    	deployementAndStartProcess();
//    	claim();
        //���������  
        String assignee = "��ɮ";  
        List<Task> list = processEngine.getTaskService()//  
                        .createTaskQuery()//  
                        .taskAssignee(assignee)//��������Ĳ�ѯ  
                        .list();  
        if(list!=null && list.size()>0){  
            for(Task task:list){  
                System.out.println("����ID��"+task.getId());  
                System.out.println("����İ����ˣ�"+task.getAssignee());  
                System.out.println("�������ƣ�"+task.getName());  
                System.out.println("����Ĵ���ʱ�䣺"+task.getCreateTime());  
                System.out.println("����ʵ��ID��"+task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
    }  
      
    /**��ѯ������*/  
    @Test  
    public void findGroupTaskList(){  
        //���������  
    	deployementAndStartProcess();
        String candidateUser = "��˽�";  
        List<Task> list = processEngine.getTaskService()//  
                        .createTaskQuery()//  
                        .taskCandidateUser(candidateUser)//�����ߣ��������ѯ  
                        .list();  
        if(list!=null && list.size()>0){  
            for(Task task:list){  
                System.out.println("����ID��"+task.getId());  
                System.out.println("����İ����ˣ�"+task.getAssignee());  
                System.out.println("�������ƣ�"+task.getName());  
                System.out.println("����Ĵ���ʱ�䣺"+task.getCreateTime());  
                System.out.println("����ʵ��ID��"+task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
    }  
      
    /**�������*/  
    @Test  
    public void completeTask(){  
        //����ID  
    	
    	Map<String,Object> map = new HashMap<String, Object>();
        String taskId = "12508";  
        map.put("testInteger", 1);
        map.put("end", 1);
        processEngine.getTaskService()//  
                        .complete(taskId,map);  
        System.out.println("�������"+taskId);  
    }  
    
    @Test
    public void completeSecondTask(){
    	Map<String,Object> map = new HashMap<String, Object>();
        String taskId = "15005";  
        map.put("asdd", 1);
        map.put("end", 1);
        processEngine.getTaskService()//  
                        .complete(taskId,map);  
        System.out.println("�������"+taskId);  
    }
      
    /**��ѯ����ִ�е��������б�*/  
    @Test  
    public void findGroupCandidate(){  
        //����ID  
        String taskId = "5909";  
        List<IdentityLink> list = processEngine.getTaskService()//  
                        .getIdentityLinksForTask(taskId);  
        if(list!=null && list.size()>0){  
            for(IdentityLink identityLink:list){  
                System.out.println("����ID��"+identityLink.getTaskId());  
                System.out.println("����ʵ��ID��"+identityLink.getProcessInstanceId());  
                System.out.println("�û�ID��"+identityLink.getUserId());  
                System.out.println("��������ɫID��"+identityLink.getGroupId());  
                System.out.println("#########################################");  
            }  
        }  
    }  
      
    /**��ѯ��ʷ���������б�*/  
    @Test  
    public void findHistoryGroupCandidate(){  
        //����ʵ��ID  
        String processInstanceId = "5905";  
        List<HistoricIdentityLink> list = processEngine.getHistoryService()  
                        .getHistoricIdentityLinksForProcessInstance(processInstanceId);  
        if(list!=null && list.size()>0){  
            for(HistoricIdentityLink identityLink:list){  
                System.out.println("����ID��"+identityLink.getTaskId());  
                System.out.println("����ʵ��ID��"+identityLink.getProcessInstanceId());  
                System.out.println("�û�ID��"+identityLink.getUserId());  
                System.out.println("��������ɫID��"+identityLink.getGroupId());  
                System.out.println("#########################################");  
            }  
        }  
    }  
      
    /**��������ָ����������(ʰȡ����)*/  
    @Test  
    public void claim(){  
//    	deployementAndStartProcess();
        //����ID  
        String taskId = "10008";  
        //��������İ�����  
        String userId = "��ɮ";  
        processEngine.getTaskService()//  
                    .claim(taskId, userId);  
    }  
      
    /**�����������ٻ��˵�������ǰ�᣺֮ǰ���������������*/  
    @Test  
    public void setAssignee(){  
        //����ID  
        String taskId = "6308";  
        processEngine.getTaskService()//  
                        .setAssignee(taskId, null);  
    }  
      
    /**������������ӳ�Ա*/  
    @Test  
    public void addGroupUser(){  
        //����ID  
        String taskId = "6308";  
        //����������ĳ�Ա  
        String userId = "����";  
        processEngine.getTaskService()//  
                    .addCandidateUser(taskId, userId);  
    }  
      
    /**����������ɾ����Ա*/  
    @Test  
    public void deleteGroupUser(){  
        //����ID  
        String taskId = "6308";  
        //����������ĳ�Ա  
        String userId = "��˽�";  
        processEngine.getTaskService()//  
                    .deleteCandidateUser(taskId, userId);  
    }  
    
}