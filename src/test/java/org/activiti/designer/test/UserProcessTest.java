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

	//�����������  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
	@Test
	public void startProcess() throws Exception {
		Deployment deployment = processEngine.getRepositoryService()//  
                .createDeployment()//�����������  
                .addInputStream("myProcess.bpmn20.xml", file)//���������Դ�ļ�  
                .deploy();  
//		System.out.println("����ID��"+deployment.getId()); 
		//2.��������  
        ProcessInstance pi = processEngine.getRuntimeService()//  
                .startProcessInstanceByKey("myProcess");  
        System.out.println("pid:" + pi.getId());  
	}
	
	/**��ѯ�ҵĸ�������,û��ִ�н��*/  
    @Test  
    public void findPersonalTaskList() throws Exception{  
        // ���������  
    	startProcess();
    	
        String assignee = "СA";  
        List<Task> list = processEngine.getTaskService()//  
                .createTaskQuery()//  
                .taskCandidateUser(assignee)// ��������Ĳ�ѯ  
                .list();  
        
        if (list != null && list.size() > 0) {  
            for (Task task : list) {  
                System.out.println("����ID��" + task.getId());  
                System.out.println("����İ����ˣ�" + task.getAssignee());  
                System.out.println("�������ƣ�" + task.getName());  
                System.out.println("����Ĵ���ʱ�䣺" + task.getCreateTime());  
                System.out.println("����ʵ��ID��" + task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
        TaskService task = processEngine.getTaskService();
        Task ts = task.createTaskQuery().singleResult();
		System.out.println(ts.getName()+"==="+ts.getId()+"�����ˣ�"+ts.getAssignee()+"��ѡ��"+ts.getProcessInstanceId());
		task.complete(ts.getId());
		
		List<Task> list1 = processEngine.getTaskService()//  
                .createTaskQuery()//  
                .taskAssignee(assignee)// ��������Ĳ�ѯ  
                .list();  
        
        if (list1 != null && list1.size() > 0) {  
            for (Task task1 : list1) {  
                System.out.println("����ID��" + task1.getId());  
                System.out.println("����İ����ˣ�" + task1.getAssignee());  
                System.out.println("�������ƣ�" + task1.getName());  
                System.out.println("����Ĵ���ʱ�䣺" + task1.getCreateTime());  
                System.out.println("����ʵ��ID��" + task1.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
		
    }  
    
    @Test  
    public void findGroupCandidate() throws Exception{  
    	System.out.println("asdf");
    	// ���������  
    	startProcess();
        //����ID  
        String taskId = "9";  
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
    /**��ѯ��ʷ���������б�
     * @throws Exception */  
    @Test  
    public void findHistoryGroupCandidate() throws Exception{  
    	findPersonalTaskList();
    	System.out.println("��ʷ����");
        //����ʵ��ID  
        String processInstanceId = "5";  
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
}
