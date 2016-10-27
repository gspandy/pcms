package com.pujjr.jbpm.core.exception;

public class NoAssigneeException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAssigneeException()
	{
		super();
	}
	
	public NoAssigneeException(String msg)
	{
		super(msg);
	}
}
