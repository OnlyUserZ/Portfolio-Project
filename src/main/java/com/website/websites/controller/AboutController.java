package com.website.websites.controller;

import java.security.Identity;
import java.util.Optional;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.websites.dto.AboutDTO;
import com.website.websites.entity.About;
import com.website.websites.service.AboutService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/about")
@RequiredArgsConstructor
public class AboutController {
       
	    private final AboutService aboutService;
	    
	    
	    @GetMapping
	    public AboutDTO getAbout() {
	    	return aboutService.getAbout();
	    }
	    
	    @PostMapping
	    public About createAbout(@Valid @RequestBody AboutDTO dto) {
	    	return aboutService.createAbout(dto);
	    }
	    
	    @PutMapping("/{id}")
	    public AboutDTO updateAbout(@PathVariable Long id, @Valid @RequestBody AboutDTO dto) {
	        
	        if (!id.equals(dto.getId())) {
	            throw new IllegalArgumentException("ID path parametresi ile DTO ID uyuşmuyor");
	        }
	        return aboutService.updateAbout(dto);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteAbout(@PathVariable Long id) {
	    	 aboutService.deleteAbout(id);
	    	
	    }
}
