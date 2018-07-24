package test.spring.config;

import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import test.spring.entity.Authority;
import test.spring.entity.User;
import test.spring.service.AuthorityService;
import test.spring.service.UserDetailService;

@Component
public class InitializeSetUPData implements ApplicationListener<ContextRefreshedEvent> {

	private Logger logger = LoggerFactory.getLogger(InitializeSetUPData.class);

	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";
	private static final String ADMIN_EMAIL_ID = "am.pruthi@gmail.com";
	private static final String ADMIN_PASSWORD = "admin";

	@Autowired
	UserDetailService userDetailService;
	@Autowired
	AuthorityService userAuthorityService;

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		
		logger.info("#################################################################################");
		logger.info("#######################       SETTING INITIAL DATA         ######################");
		logger.info("#################################################################################");

		if (userDetailService.findUserByEmail(ADMIN_EMAIL_ID) == null) {
			Authority authority = new Authority();
			authority.setName(ADMIN);
			userAuthorityService.save(authority);

			Authority userAuthority = new Authority();
			userAuthority.setName(USER);
			userAuthorityService.save(userAuthority);
			
			User user = new User();
			user.setEmailid(ADMIN_EMAIL_ID);
			user.setName("Amit Pruthi");
			user.setEnabled(true);
			user.setPassword(ADMIN_PASSWORD);

			Authority adminAuth = userAuthorityService.findByName(ADMIN);
			user.setAuthorities(new ArrayList<Authority>(Arrays.asList(adminAuth)));

			userDetailService.saveUser(user);
			logger.info("Initial admin login credentials added!");
		} else {
			logger.info("Admin credentials already exist!");

		}
	}
}