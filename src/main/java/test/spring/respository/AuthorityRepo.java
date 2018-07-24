package test.spring.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.spring.entity.Authority;

@Repository("userAuthorityRepository")
public interface AuthorityRepo extends JpaRepository<Authority, Integer>{
	Authority findByName(String authority);

}
