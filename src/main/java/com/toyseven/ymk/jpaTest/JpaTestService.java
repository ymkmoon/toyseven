package com.toyseven.ymk.jpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class JpaTestService {
	
	@Autowired
	private JpaTestRepository jpaTestRepository;
	
	public List<AdminItem> findAll() {
		List<AdminItem> jpaTest = new ArrayList<>();
		jpaTestRepository.findAll().forEach(e -> jpaTest.add(e));
		
		return jpaTest;
	}
	
	public Optional<AdminItem> findById(Integer mbrNo) {
		Optional<AdminItem> adminItem = jpaTestRepository.findById(mbrNo);
		return adminItem;
	}
	
	public void deleteById(Integer mbrNo) {
		jpaTestRepository.deleteById(mbrNo);
	}
	
	public AdminItem save(AdminItem adminItem) {
		jpaTestRepository.save(adminItem);
		return adminItem;
	}
	
	public void updateById(Integer mbrNo, AdminItem adminItem) {
		Optional<AdminItem> e = jpaTestRepository.findById(mbrNo);
		
		if (e.isPresent()) {
			e.get().setId(adminItem.getId());
			e.get().setUserName(adminItem.getUserName());
			e.get().setName(adminItem.getName());
			
			e.get().setPassword(adminItem.getPassword());
			e.get().setRole(adminItem.getRole());
			e.get().setModified_at(adminItem.getModified_at());
			e.get().setStatus(adminItem.getStatus());
			jpaTestRepository.save(adminItem);
		}
	}

}
