package com.hotel.hotel.domain.form;

import com.hotel.hotel.annotation.TimeLineTerm;
import com.hotel.hotel.annotation.TimeValidChecker;
import java.io.Serializable;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@TimeValidChecker(message = "The End Time Cannot Faster Than The Start Time")
public class ReservationForm implements Serializable {

	@NotNull(message = "Required Field")
	@TimeLineTerm(message = "Please input by 30 minutes term")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@NotNull(message = "Required Field")
	@TimeLineTerm(message = "Please input by 30 minutes term")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;

}
