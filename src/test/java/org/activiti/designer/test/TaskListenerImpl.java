package org.activiti.designer.test;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

@SuppressWarnings("serial")
public class TaskListenerImpl implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		//ָ��������  
        delegateTask.addCandidateUser("�����");  
        delegateTask.addCandidateUser("��˽�");  
	}

}
