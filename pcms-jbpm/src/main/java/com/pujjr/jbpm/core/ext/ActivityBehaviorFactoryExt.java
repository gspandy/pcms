package com.pujjr.jbpm.core.ext;

import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;

public class ActivityBehaviorFactoryExt extends DefaultActivityBehaviorFactory 
{
	private ExclusiveGatewayActivityBehaviorExt exclusiveGatewayActivityBehaviorExt;  
    
    /** 
     * ͨ��Spring����ע���µķ�֧������Ϊִ���� 
     * @param exclusiveGatewayActivityBehaviorExt 
     */  
    public void setExclusiveGatewayActivityBehaviorExt(ExclusiveGatewayActivityBehaviorExt exclusiveGatewayActivityBehaviorExt) {  
        this.exclusiveGatewayActivityBehaviorExt = exclusiveGatewayActivityBehaviorExt;  
    }  
      
    //��д�����еķ�֧������Ϊִ����  
    @Override  
    public ExclusiveGatewayActivityBehavior createExclusiveGatewayActivityBehavior(ExclusiveGateway exclusiveGateway) {  
        return exclusiveGatewayActivityBehaviorExt;  
    }  
}
