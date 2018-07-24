package test.spring.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.spring.entity.User;

@Repository("userDetailRepository")
public interface UserDetailRepo extends JpaRepository<User, Long> {
	
	User findByEmailid(String email);

	User findByName(String name);
}