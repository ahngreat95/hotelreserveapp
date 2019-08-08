package com.hotel.hotel.config;

import java.io.IOException;
import java.time.Duration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class SessionTimeoutAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	public final Duration sessionTimeout;

	public SessionTimeoutAuthSuccessHandler(Duration sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws ServletException, IOException {
		req.getSession().setMaxInactiveInterval(Math.toIntExact(sessionTimeout.getSeconds()));
		super.onAuthenticationSuccess(req, res, auth);
	}
}
