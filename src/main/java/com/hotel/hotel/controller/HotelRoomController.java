package com.hotel.hotel.controller;

import com.hotel.hotel.domain.model.ReservableRoom;
import com.hotel.hotel.service.HotelRoomService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotelrooms")
public class HotelRoomController {

	@Autowired
	HotelRoomService hotelRoomService;

	@GetMapping(value = "{date}")
	public String hotelRoomList(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@PathVariable("date")
			LocalDate date,
			Model model) {
		List<ReservableRoom> hotelRoomList = hotelRoomService.findReservableRoomList(date);
		model.addAttribute("hotelRoomList", hotelRoomList);
		return "hotelRoomList";
	}

	@GetMapping
	public String hotelRoomList(Model model) {
		LocalDate currentDay = LocalDate.now();
		model.addAttribute("date", currentDay);
		return hotelRoomList(currentDay, model);
	}
}
