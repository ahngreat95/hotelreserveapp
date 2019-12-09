package com.hotel.hotel.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservableRoomId implements Serializable {

	private Integer roomId;
	private LocalDate reservedDate;
}
