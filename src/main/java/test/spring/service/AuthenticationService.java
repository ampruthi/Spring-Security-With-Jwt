package test.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import test.spring.dto.JwtRequest;
import test.spring.entity.User;
import test.spring.entity.UserDetail;
import test.spring.jwt.JwtUtil;

@Component
public class AuthenticationService {

	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	JwtUtil jwtutil;
	
	public String generateToken(JwtRequest jwtRequest) {
		// Perform the security
		final Authentication authentication = authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		        		jwtRequest.getEmailId(),
		        		jwtRequest.getPassword()
		        )
		);
		SecurityContextHolder.getContext().setAuthentication(authentication); 
		
		User user = userDetailService.findUserByEmail(jwtRequest.getEmailId());
		
		UserDetail userDetail = new UserDetail(user);
		
		return jwtutil.generateToken(userDetail);
	}
}
