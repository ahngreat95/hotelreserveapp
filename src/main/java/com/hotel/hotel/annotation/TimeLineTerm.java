package com.hotel.hotel.annotation;

import com.hotel.hotel.valid.TimeLineTermValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {TimeLineTermValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeLineTerm {

	String message() default "com.example.demo.annotation.TimeLineTerm.message";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};


	@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List{
		TimeLineTerm[] values();
	}
}
