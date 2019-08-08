package com.hotel.hotel.service;

import com.hotel.hotel.domain.dto.UserRegistrationDTO;
import com.hotel.hotel.domain.dto.UserUpdateDTO;
import com.hotel.hotel.domain.model.Reservation;
import com.hotel.hotel.domain.model.ReservingUserDetails;
import com.hotel.hotel.domain.model.RoleName;
import com.hotel.hotel.domain.model.User;
import com.hotel.hotel.domain.repository.ReservationRepository;
import com.hotel.hotel.domain.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReservationRepository reservationRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findById(s).orElseThrow(() -> new UsernameNotFoundException(s + "Is Not Found"));
		return new ReservingUserDetails(user);
	}

	public User findByUserId(String id) {
		return userRepository.findById(id).orElse(null);
	}

	public User save(UserRegistrationDTO registration) {
		User user = new User();
		user.setUserId(registration.getUserId());
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		user.setRoleName(RoleName.USER);
		return userRepository.save(user);
	}

	public User update(UserUpdateDTO user){
		User updateUser = findByUserId(SecurityContextHolder.getContext().getAuthentication().getName());
		updateUser.setFirstName(user.getFirstName());
		updateUser.setLastName(user.getLastName());
		updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(updateUser);
	}

	public void delete(String userId){
		User user = findByUserId(userId);
		userRepository.delete(user);
	}

	public List<Reservation> findUserReservationList(User user){
		return reservationRepository.findReservationsByUser(user);
	}
}
