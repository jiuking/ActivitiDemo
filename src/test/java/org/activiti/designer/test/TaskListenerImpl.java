package org.activiti.designer.test;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

@SuppressWarnings("serial")
public class TaskListenerImpl implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		//指定组任务  
        delegateTask.addCandidateUser("孙悟空");  
        delegateTask.addCandidateUser("猪八戒");  
	}

}
