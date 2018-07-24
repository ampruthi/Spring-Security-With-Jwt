package test.spring.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/service")
public class ServiceProviderController {

	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public String getloggedinUser() {

		Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
		if (currentAuthentication != null)
			return currentAuthentication.getName();
		else
			return "Not Found";
	}
}
