package com.chase.framerwork.exception;

/**
 * 业务Exception
 * @author Chase
 *
 */
public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3858969602927135737L;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String message){
		super(message);
	}
}
