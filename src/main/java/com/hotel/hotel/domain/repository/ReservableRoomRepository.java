package com.hotel.hotel.domain.repository;

import com.hotel.hotel.domain.model.ReservableRoom;
import com.hotel.hotel.domain.model.ReservableRoomId;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ReservableRoomRepository extends JpaRepository<ReservableRoom, ReservableRoomId> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	ReservableRoom findOneForUpdateByReservableRoomId(ReservableRoomId reservableRoomId);
	List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(LocalDate reservedDate);
}
