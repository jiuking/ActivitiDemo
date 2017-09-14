package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcess {

	private String filename = "C:\\Users\\Administrator\\git\\ActivitiDemo\\src\\main\\resources\\Activiti\\activitiDemo.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml",
				new FileInputStream(filename)).deploy();
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		runtimeService.startProcessInstanceByKey("myProcess");
		Map<String, Object> variableMap = new HashMap<String, Object>();
		TaskService task = engine.getTaskService();
		
		Task ts = task.createTaskQuery().singleResult();
		System.out.println(ts.getName()+"==="+ts.getId()+"�����ˣ�"+ts.getAssignee()+"��ѡ��"+ts.getProcessInstanceId());
		Map<String, Object> variables = new HashMap<String, Object>();  
	    variables.put("leaderAgree", 0);  
		task.complete(ts.getId(),variables);
		
		ts = task.createTaskQuery().singleResult();
		System.out.println(ts.getName()+"==="+ts.getId()+"ʵ��id��"+ts.getProcessInstanceId());
		task.complete(ts.getId());
		
		String processInstanceId="5";  
	    List<HistoricTaskInstance> list = engine.getHistoryService()//����ʷ���ݣ���ʷ����ص�service  
	            .createHistoricTaskInstanceQuery()//������ʷ����ʵ����ѯ  
	            .processInstanceId(processInstanceId)  
//	              .taskAssignee(taskAssignee)//ָ����ʷ����İ�����  
	            .list();  
	    if(list!=null && list.size()>0){  
	        for(HistoricTaskInstance hti:list){  
	            System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());  
	            System.out.println("1111################################");  
	        }  
	    }   
		
		ts = task.createTaskQuery().singleResult();
		System.out.println(ts.getName()+"==="+ts.getId());
		task.complete(ts.getId());
		
//		ts = task.createTaskQuery().singleResult();
//		System.out.println(ts.getName()+"==="+ts.getId());
		
		variableMap.put("name", "Activiti");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		
	}
}