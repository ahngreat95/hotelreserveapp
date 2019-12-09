package com.hotel.hotel.controller;

import com.hotel.hotel.domain.form.ReservationForm;
import com.hotel.hotel.domain.model.ReservableRoom;
import com.hotel.hotel.domain.model.ReservableRoomId;
import com.hotel.hotel.domain.model.Reservation;
import com.hotel.hotel.domain.model.ReservingUserDetails;
import com.hotel.hotel.exception.AlreadyReservedException;
import com.hotel.hotel.exception.ImpossibleToReserveException;
import com.hotel.hotel.service.HotelRoomService;
import com.hotel.hotel.service.ReservationService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations/{date}/{roomId}")
public class ReservationController {

	@Autowired
	HotelRoomService hotelRoomService;

	@Autowired
	ReservationService reservationService;

	@ModelAttribute
	public ReservationForm setUpForm(){
		return ReservationForm.builder()
				.startTime(LocalTime.of(9,0))
				.endTime(LocalTime.of(10,0))
				.build();
	}

	@GetMapping
	public String reserveForm(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@PathVariable("date")LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		ReservableRoomId reservableRoomId = ReservableRoomId.builder()
				.roomId(roomId)
				.reservedDate(date)
				.build();

		List<Reservation> reservationList = reservationService.findReservationList(reservableRoomId);

		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0,0), time -> time.plusMinutes(30))
				.limit(48)
				.collect(Collectors.toList());

		model.addAttribute("date", date);
		model.addAttribute("hotelRoom", hotelRoomService.findHotelRoom(roomId));
		model.addAttribute("reservationList", reservationList);
		model.addAttribute("timeList", timeList);
		model.addAttribute("reservationForm", setUpForm());
		model.addAttribute("userId", authentication.getName());

		return "reserveForm";
	}

	@PostMapping
	public String reserve(
			@Validated ReservationForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal ReservingUserDetails reservingUserDetails,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId,
			Model model){

		if(bindingResult.hasErrors()){
			return reserveForm(date, roomId, model);
		}

		ReservableRoom reservableRoom = ReservableRoom.builder()
				.reservableRoomId(ReservableRoomId.builder()
						.reservedDate(date)
						.roomId(roomId)
						.build())
				.build();

		Reservation reservation = new Reservation();
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setReservableRoom(reservableRoom);
		reservation.setUser(reservingUserDetails.getUser());

		try{

			reservationService.reserve(reservation);

		}catch (ImpossibleToReserveException | AlreadyReservedException e){

			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}

		return "redirect:/reservations/{date}/{roomId}";
	}


	@PostMapping(path = "/{reservationId}", params = "cancel")
	public String cancel(
			@AuthenticationPrincipal ReservingUserDetails reservingUserDetails,
			@PathVariable("reservationId") Integer reservationId,
			@PathVariable("roomId") Integer roomId,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@PathVariable("date") LocalDate date,
			Model model){

		try{
			Reservation reservation = reservationService.findReservation(reservationId);
			reservationService.cancel(reservation);
		}catch(AccessDeniedException e){
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}
		return "redirect:/reservations/{date}/{roomId}";
	}
}
