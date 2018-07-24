package test.spring.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import test.spring.AuthEntryPoint;
import test.spring.dto.JwtRequest;
import test.spring.dto.UserDto;
import test.spring.service.AuthenticationService;
import test.spring.service.UserDetailService;

@RestController
@RequestMapping(value = "/user")
public class UserManagerController {

	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private AuthEntryPoint authEntryPoint;
	@Autowired
	private UserDetailService userDetailService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String login(@RequestBody JwtRequest requestBody, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String token = null;

		try {
			token = authenticationService.generateToken(requestBody);
		} catch (AuthenticationException e) {
			SecurityContextHolder.clearContext();
			authEntryPoint.commence(request, response, e);
		}

		return token;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String login(@RequestBody UserDto requestBody, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String password = userDetailService.createUser(requestBody);
		
		return "Your password is: "+password;
	}

}
