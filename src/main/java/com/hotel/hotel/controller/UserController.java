package com.hotel.hotel.controller;

import com.hotel.hotel.domain.dto.UserUpdateDTO;
import com.hotel.hotel.domain.model.Reservation;
import com.hotel.hotel.domain.model.User;
import com.hotel.hotel.service.ReservationService;
import com.hotel.hotel.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReservationService reservationService;

	@ModelAttribute("user")
	public UserUpdateDTO userUpdateDTO() {
		return new UserUpdateDTO();
	}

	@GetMapping
	public String showUpdateForm(Model model) {
		return "updateUserForm";
	}

	@PutMapping("/{userId}")
	public String updateUserAccount(
			@ModelAttribute("user") @Valid UserUpdateDTO userDto,
			@PathVariable String userId,
			BindingResult result){

		userId = SecurityContextHolder.getContext().getAuthentication().getName();

		if (result.hasErrors()){
			return "updateUserForm";
		}

		userService.update(userDto);
		return "redirect:/index";
	}

	@GetMapping("/reserved")
	public String reservedHotelRoomList(
			Model model){

		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUserId(userId);
		List<Reservation> reservationList = userService.findUserReservationList(user);
		model.addAttribute("reservationList", reservationList);
		return "profile";
	}


	@RequestMapping("/deactivate")
	public String loadDeactivation(){
		return "deactivation";
	}

	@DeleteMapping("/deactivate/{userId}")
	public String deactivate(
			@PathVariable String userId){
		User user = userService.findByUserId(userId);
		reservationService.cancelAll(user);
		userService.delete(userId);
		return "redirect:/logout";

	}
}
