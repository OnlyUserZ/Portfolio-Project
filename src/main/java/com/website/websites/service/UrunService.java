package com.website.websites.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.website.websites.dto.UrunDTO;
import com.website.websites.entity.Kategori;
import com.website.websites.entity.Urun;
import com.website.websites.repository.KategoriRepository;
import com.website.websites.repository.UrunRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrunService {

    private final KategoriRepository kategoriRepository;
    private final UrunRepository urunRepository;
    

    public Urun addUrun(UrunDTO dto) {
        Urun urun = new Urun();
        urun.setFiyat(dto.getFiyat());
        urun.setMarka(dto.getMarka());
        urun.setName(dto.getName());

        Set<Kategori> kategoriler = new HashSet<>();

        for (Long kategoriId : dto.getKategoriId()) {
            Kategori kategori = kategoriRepository.findById(kategoriId)
                    .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + kategoriId));
            kategoriler.add(kategori);
        }

        urun.setKategoriler(kategoriler);
        return urunRepository.save(urun);
    }

    @Transactional
    public void deleteUrun(Long id) {
        Urun urun = urunRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Urun bulunamadı: " + id));
        urunRepository.delete(urun);
    }

    @Transactional
    public void updateUrun(Long id, UrunDTO dto) {
        Urun urun = urunRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Urun bulunamadı: " + id));

        urun.setFiyat(dto.getFiyat());
        urun.setMarka(dto.getMarka());
        urun.setName(dto.getName());

        Set<Kategori> kategoriler = new HashSet<>();

        for (Long kategoriId : dto.getKategoriId()) {
            Kategori kategori = kategoriRepository.findById(kategoriId)
                    .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + kategoriId));
            kategoriler.add(kategori);
        }

        urun.setKategoriler(kategoriler);
        urunRepository.save(urun);
    }
    
    public Page<Urun> getAllUrun(Pageable pageable) {
    	return urunRepository.findAll(pageable);
    }
    
    
}
