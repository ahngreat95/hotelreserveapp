package com.hotel.hotel.controller;

import com.hotel.hotel.domain.dto.UserRegistrationDTO;
import com.hotel.hotel.domain.model.User;
import com.hotel.hotel.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {


	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserRegistrationDTO userRegistrationDTO() {
		return new UserRegistrationDTO();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registrationForm";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDTO userDto,
			BindingResult result){

		User existingUser = userService.findByUserId(userDto.getUserId());
		if (existingUser != null){
			result.rejectValue("userId", null, "The User ID is Already Taken");
		}
		if (result.hasErrors()){
			return "registrationForm";
		}

		userService.save(userDto);
		return "redirect:/loginForm";
	}
}
