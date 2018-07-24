package test.spring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import test.spring.dto.UserDto;
import test.spring.entity.Authority;
import test.spring.entity.User;
import test.spring.respository.AuthorityRepo;
import test.spring.respository.UserDetailRepo;

@Component("userService")
public class UserDetailServiceImp implements UserDetailService {

	@Autowired
	private UserDetailRepo userDetailRepository;
	@Autowired
	private AuthorityRepo userAuthorityRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByEmail(String email) {
		return userDetailRepository.findByEmailid(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		userDetailRepository.save(user);
	}

	@Override
	public String createUser(UserDto userDto) {
		String code = (100000 + (new Random().nextInt(900000))) + "";
		User user = new User();
		user.setEmailid(userDto.getEmailid());
		user.setName(userDto.getName());
		user.setEnabled(true);
		user.setPassword(bCryptPasswordEncoder.encode(code));

		Authority authority = userAuthorityRepo.findByName("USER");
		user.setAuthorities(new ArrayList<Authority>(Arrays.asList(authority)));

		userDetailRepository.save(user);
		return code;
	}

}
