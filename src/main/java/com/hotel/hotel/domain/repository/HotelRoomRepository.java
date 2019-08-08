package com.hotel.hotel.domain.repository;

import com.hotel.hotel.domain.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer> {
}
