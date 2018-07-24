package test.spring.service;

import test.spring.dto.UserDto;
import test.spring.entity.User;

public interface UserDetailService {

	public User findUserByEmail(String email);

	public void saveUser(User user);

	public String createUser(UserDto userDto);
}
