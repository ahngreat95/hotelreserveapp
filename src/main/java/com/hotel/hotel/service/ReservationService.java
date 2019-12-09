package com.hotel.hotel.service;

import com.hotel.hotel.domain.model.ReservableRoom;
import com.hotel.hotel.domain.model.ReservableRoomId;
import com.hotel.hotel.domain.model.Reservation;
import com.hotel.hotel.domain.model.User;
import com.hotel.hotel.domain.repository.ReservableRoomRepository;
import com.hotel.hotel.domain.repository.ReservationRepository;
import com.hotel.hotel.exception.AlreadyReservedException;
import com.hotel.hotel.exception.ImpossibleToReserveException;
import com.hotel.hotel.exception.ReservationIdNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservableRoomRepository reservableRoomRepository;

	public List<Reservation> findReservationList(ReservableRoomId reservableRoomId){
		return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
	}

	public Reservation reserve(Reservation reservation){
		ReservableRoomId reservableRoomId = Optional.ofNullable(reservation.getReservableRoom().getReservableRoomId())
				.orElseThrow(() -> new ImpossibleToReserveException("Unavailable"));
		ReservableRoom reservableRoom = reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId);
		boolean duplication = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
				.stream()
				.anyMatch(reservation1 -> reservation1.duplicate(reservation));
		if(duplication){
			throw new AlreadyReservedException("Already Taken");
		}
		reservationRepository.save(reservation);
		return reservation;
	}


	@PreAuthorize("hasRole('ADMIN')" + " or #reservation.user.userId == principal.username")
	public void cancel(@P("reservation") Reservation reservation){
		reservationRepository.delete(reservation);
	}

	public void cancelAll(User user){
		List<Reservation> reservationList = reservationRepository.findReservationsByUser(user);
		reservationRepository.deleteAll(reservationList);
	}

	public Reservation findReservation(Integer reservationId){
		return reservationRepository
				.findById(reservationId)
				.orElseThrow(() -> new ReservationIdNotFoundException("Reservation ID Not Found"));
	}
}
