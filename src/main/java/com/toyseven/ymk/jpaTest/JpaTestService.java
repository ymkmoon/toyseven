package com.toyseven.ymk.jpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.model.AdminItem;

@Service
public class JpaTestService {
	
	@Autowired
	private JpaTestRepository jpaTestRepository;
	
	public List<AdminItem> findAll() {
		List<AdminItem> jpaTest = new ArrayList<>();
		jpaTestRepository.findAll().forEach(e -> jpaTest.add(e));
		
		return jpaTest;
	}
	
	public Optional<AdminItem> findById(Long id) {
		Optional<AdminItem> adminItem = jpaTestRepository.findById(id);
		return adminItem;
	}
	
	public void deleteById(Long id) {
		jpaTestRepository.deleteById(id);
	}
	
	public AdminItem save(AdminItem adminItem) {
		jpaTestRepository.save(adminItem);
		return adminItem;
	}
	
	public void updateById(Long id, AdminItem adminItem) {
		Optional<AdminItem> e = jpaTestRepository.findById(id);
		
		if (e.isPresent()) {
			e.get().setUsername(adminItem.getUsername());
			e.get().setName(adminItem.getName());
			
			e.get().setPassword(adminItem.getPassword());
			e.get().setRole(adminItem.getRole());
			e.get().setModifiedAt(adminItem.getModifiedAt());
			e.get().setStatus(adminItem.getStatus());
			jpaTestRepository.save(adminItem);
		}
	}

}
