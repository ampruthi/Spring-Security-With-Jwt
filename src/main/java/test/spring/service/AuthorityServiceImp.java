package test.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import test.spring.entity.Authority;
import test.spring.respository.AuthorityRepo;

@Component("authorityService")
public class AuthorityServiceImp implements AuthorityService {

	@Autowired
	AuthorityRepo authorityRepo;

	@Override
	public Authority findByName(String name) {
		return authorityRepo.findByName(name);
	}

	@Override
	public void save(Authority authority) {
		authorityRepo.save(authority);
	}

}
