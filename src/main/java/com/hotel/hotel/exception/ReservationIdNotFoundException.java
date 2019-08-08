package com.hotel.hotel.exception;

public class ReservationIdNotFoundException extends RuntimeException{
	public ReservationIdNotFoundException(String message){
		super(message);
	}
}
