package com.hotel.hotel.exception;

public class AlreadyReservedException extends RuntimeException {
	public AlreadyReservedException(String message){
		super(message);
	}
}
