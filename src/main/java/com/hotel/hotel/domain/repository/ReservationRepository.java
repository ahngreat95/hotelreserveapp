package com.hotel.hotel.domain.repository;

import com.hotel.hotel.domain.model.ReservableRoomId;
import com.hotel.hotel.domain.model.Reservation;
import com.hotel.hotel.domain.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId);
	List<Reservation> findReservationsByUser(User user);
}
