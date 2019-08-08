package com.hotel.hotel.valid;

import com.hotel.hotel.annotation.TimeValidChecker;
import com.hotel.hotel.domain.form.ReservationForm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeValidCheckerValidator implements ConstraintValidator<TimeValidChecker, ReservationForm> {

	private String message;

	@Override
	public void initialize(TimeValidChecker constraintAnnotation) {
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(ReservationForm value, ConstraintValidatorContext context) {
		if(value.getStartTime() == null || value.getEndTime() == null){
			return true;
		}
		boolean isTimeValid = value.getEndTime().isAfter(value.getStartTime());
		if(!isTimeValid){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode("endTime")
					.addConstraintViolation();
		}
		return false;
	}
}
