package test.spring.service;

import org.springframework.stereotype.Component;

import test.spring.entity.Authority;

@Component("authorityService")
public interface AuthorityService {

	public Authority findByName(String name);
	public void save(Authority authority);
}
