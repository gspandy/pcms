package com.pujjr.jbpm.core.ext;

import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;

public class ActivityBehaviorFactoryExt extends DefaultActivityBehaviorFactory 
{
	private ExclusiveGatewayActivityBehaviorExt exclusiveGatewayActivityBehaviorExt;  
    
    /** 
     * 通过Spring容器注入新的分支条件行为执行类 
     * @param exclusiveGatewayActivityBehaviorExt 
     */  
    public void setExclusiveGatewayActivityBehaviorExt(ExclusiveGatewayActivityBehaviorExt exclusiveGatewayActivityBehaviorExt) {  
        this.exclusiveGatewayActivityBehaviorExt = exclusiveGatewayActivityBehaviorExt;  
    }  
      
    //重写父类中的分支条件行为执行类  
    @Override  
    public ExclusiveGatewayActivityBehavior createExclusiveGatewayActivityBehavior(ExclusiveGateway exclusiveGateway) {  
        return exclusiveGatewayActivityBehaviorExt;  
    }  
}
