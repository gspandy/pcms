package com.pujjr.jbpm.core.ext;

import java.util.List;

import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.jbpm.core.script.GroovyEngine;
import com.pujjr.jbpm.dao.WorkflowGateWayParamMapper;
import com.pujjr.jbpm.domain.WorkflowGateWayParam;

@SuppressWarnings("serial")
public class ExclusiveGatewayActivityBehaviorExt extends ExclusiveGatewayActivityBehavior 
{
	@Autowired
	private WorkflowGateWayParamMapper gatewayParamDao;
	@Override
	protected void leave(ActivityExecution execution) {
		// TODO Auto-generated method stub
		String workflowVersionId = execution.getVariable("workflowVersionId").toString();
		String nodeId = execution.getActivity().getId();
		List<PvmTransition> outTransActs = execution.getActivity().getOutgoingTransitions();
		//获取网关参数信息
		try 
		{
			List<WorkflowGateWayParam>  gatewayParam = gatewayParamDao.selectGatewayParamsByNodeId(workflowVersionId, nodeId);
			if(gatewayParam != null && gatewayParam.size()!=0)
			{
				for(WorkflowGateWayParam o:gatewayParam)
				{
					if(o.getOutScript() !=null && o.getOutScript()!="")
					{
						//执行脚本
						Object boolVal = GroovyEngine.execScript(o.getOutScript(),execution.getId(),execution.getVariables());
						if(boolVal instanceof Boolean)
						{
							if((Boolean)boolVal==true)
							{
								for(PvmTransition outTransAct:outTransActs)
								{
									if(outTransAct.getDestination().getId().equals(o.getOutNodeId()))
									{
										execution.take(outTransAct);
										return;
									}
								}
							}
						}
						else
						{
							throw new RuntimeException("表达式:\n"+o.getOutScript()+"\n不为Boolean值");
						}
					}
				}
			}
			
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		super.leave(execution);
	}
	
}
