package com.chase.framerwork.exception;

/**
 * 系统异常
 * @author Chase
 *
 */
public class SystemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3858969602927135737L;
	
	public SystemException() {
		super();
	}
	
	public SystemException(String message){
		super(message);
	}
}
