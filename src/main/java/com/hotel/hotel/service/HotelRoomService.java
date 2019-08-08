package com.hotel.hotel.service;

import com.hotel.hotel.domain.model.HotelRoom;
import com.hotel.hotel.domain.model.ReservableRoom;
import com.hotel.hotel.domain.repository.HotelRoomRepository;
import com.hotel.hotel.domain.repository.ReservableRoomRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HotelRoomService {

	@Autowired
	ReservableRoomRepository reservableRoomRepository;

	@Autowired
	HotelRoomRepository hotelRoomRepository;

	public List<ReservableRoom> findReservableRoomList(LocalDate date){
		return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date);
	}

	public HotelRoom findHotelRoom(Integer roomId){
		return hotelRoomRepository.findById(roomId).orElse(null);
	}
}
