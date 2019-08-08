package com.hotel.hotel.valid;

import com.hotel.hotel.annotation.TimeLineTerm;
import java.time.LocalTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeLineTermValidator implements ConstraintValidator<TimeLineTerm, LocalTime> {
	@Override
	public void initialize(TimeLineTerm constraintAnnotation){}

	@Override
	public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
		if(value == null){
			return true;
		}
		return value.getMinute()%30 == 0;
	}
}
