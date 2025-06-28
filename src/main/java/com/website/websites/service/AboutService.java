package com.website.websites.service;


import java.util.Optional;
import org.springframework.stereotype.Service;
import com.website.websites.dto.AboutDTO;
import com.website.websites.entity.About;
import com.website.websites.repository.AboutRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AboutService {
	private final AboutRepository aboutRepository;
	
	public About createAbout(AboutDTO dto) {
		if(aboutRepository.count() > 0) {
			throw new RuntimeException("Zaten About Var");
		} else {
			About about = new About();
			
			about.setText(dto.getText());
			about.setUrl(dto.getUrl());
			about.setTitle(dto.getTitle());
			
		return aboutRepository.save(about);
		}
		}
		
	public AboutDTO getAbout() {
		 About about = aboutRepository.findAll()
                 .stream()
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("About bulunamadı"));
 //eğer bir nesnenin belirli özelliklerini dto olarak döneceksen eğer bunu listeli olarak döneceksen map kullansan yeter ama eğer bunu
// tek olarak döneceksen yani bir tane şeyi döneceksen o zaman stream findfirstli mevzuyu kullanacaksın 		 
                 AboutDTO dto = new AboutDTO();
                 dto.setId(about.getId());
                 dto.setText(about.getText());
                 dto.setUrl(about.getUrl());
                 dto.setTitle(about.getTitle());
                      
                       return dto;
	}
	@Transactional	
	public AboutDTO updateAbout(AboutDTO dto) {
	    Optional<About> optionalAbout = aboutRepository.findById(dto.getId());
	    
	    if (optionalAbout.isPresent()) {
	        About about = optionalAbout.get();
	        
	        about.setText(dto.getText());
	        about.setTitle(dto.getTitle());
	        about.setUrl(dto.getUrl());

	        About updated = aboutRepository.save(about);

	        
	        AboutDTO updatedDto = new AboutDTO();
	        updatedDto.setId(updated.getId());
	        updatedDto.setText(updated.getText());
	        updatedDto.setTitle(updated.getTitle());
	        updatedDto.setUrl(updated.getUrl());

	        return updatedDto;
	    } else {
	        throw new RuntimeException("About bulunamadı");
	    }
	}
    @Transactional
	public void deleteAbout(Long id) {
		Optional<About> exist = aboutRepository.findById(id);
		if(exist.isPresent()) {
			About about = exist.get();
			 aboutRepository.delete(about);
		}
		else { throw new RuntimeException("bulamadık"); }
	}
	
	}
