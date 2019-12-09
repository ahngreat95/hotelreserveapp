package com.hotel.hotel.domain.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;

	private LocalTime startTime;

	private LocalTime endTime;

	@ManyToOne
	@JoinColumns({@JoinColumn(name = "reserved_date"),
		@JoinColumn(name = "room_id")})
	private ReservableRoom reservableRoom;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public boolean duplicate(Reservation reservation){
		if(!Objects.equals(reservableRoom.getReservableRoomId(), reservation.reservableRoom.getReservableRoomId())){
			return false;
		}
		if(startTime.equals(reservation.startTime) && endTime.equals(reservation.endTime)){
			return true;
		}
		return reservation.endTime.isAfter(startTime) && endTime.isAfter(reservation.startTime);
	}
}
