package com.hotel.hotel.exception;

public class CannotCancelException extends RuntimeException{
	public CannotCancelException(String message){
		super(message);
	}
}
