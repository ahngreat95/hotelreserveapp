package com.hotel.hotel.annotation;

import com.hotel.hotel.valid.TimeValidCheckerValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {TimeValidCheckerValidator.class})
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface TimeValidChecker {
	String message() default "{com.example.demo.annotation.TimeValidChecker.message";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List{
		TimeValidChecker[] values();
	}
}
