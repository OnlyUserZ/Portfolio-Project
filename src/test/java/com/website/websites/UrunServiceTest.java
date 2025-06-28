
//Testler ChatGPT'ye Yazdırılmıştır
package com.website.websites;

import com.website.websites.dto.UrunDTO;
import com.website.websites.entity.Kategori;
import com.website.websites.entity.Urun;
import com.website.websites.repository.KategoriRepository;
import com.website.websites.repository.UrunRepository;
import com.website.websites.service.UrunService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrunServiceTest {

    @InjectMocks
    private UrunService urunService;

    @Mock
    private KategoriRepository kategoriRepository;

    @Mock
    private UrunRepository urunRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------ ADD ------------------

    @Test
    void addUrun_shouldSaveUrun_whenValidDTO() {
        UrunDTO dto = new UrunDTO();
        dto.setFiyat(199.99);  // double kullandık
        dto.setMarka("KenanMarka");
        dto.setName("Klavye");
        dto.setKategoriId(new HashSet<>(List.of(1L, 2L)));  // Set<Long> olarak

        Kategori k1 = new Kategori(); k1.setId(1L);
        Kategori k2 = new Kategori(); k2.setId(2L);

        when(kategoriRepository.findById(1L)).thenReturn(Optional.of(k1));
        when(kategoriRepository.findById(2L)).thenReturn(Optional.of(k2));

        when(urunRepository.save(any(Urun.class))).thenAnswer(i -> i.getArgument(0));

        Urun result = urunService.addUrun(dto);

        assertNotNull(result);
        assertEquals("KenanMarka", result.getMarka());
        assertEquals("Klavye", result.getName());
        assertEquals(2, result.getKategoriler().size());
        verify(urunRepository).save(any(Urun.class));
    }

    @Test
    void addUrun_shouldThrow_whenKategoriNotFound() {
        UrunDTO dto = new UrunDTO();
        dto.setFiyat(99.0);
        dto.setMarka("ABC");
        dto.setName("Mouse");
        dto.setKategoriId(new HashSet<>(List.of(999L)));

        when(kategoriRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> urunService.addUrun(dto));
        assertTrue(ex.getMessage().contains("Kategori bulunamadı"));
    }

    // ------------ UPDATE ------------------

    @Test
    void updateUrun_shouldUpdateAndSave_whenValid() {
        Long urunId = 1L;

        UrunDTO dto = new UrunDTO();
        dto.setFiyat(299.0);
        dto.setMarka("YeniMarka");
        dto.setName("YeniUrun");
        dto.setKategoriId(new HashSet<>(List.of(3L)));

        Urun existing = new Urun();
        existing.setId(urunId);

        Kategori kategori = new Kategori();
        kategori.setId(3L);

        when(urunRepository.findById(urunId)).thenReturn(Optional.of(existing));
        when(kategoriRepository.findById(3L)).thenReturn(Optional.of(kategori));

        urunService.updateUrun(urunId, dto);

        assertEquals("YeniMarka", existing.getMarka());
        assertEquals("YeniUrun", existing.getName());
        assertEquals(299.0, existing.getFiyat());
        assertEquals(1, existing.getKategoriler().size());
        verify(urunRepository).save(existing);
    }

    @Test
    void updateUrun_shouldThrow_whenUrunNotFound() {
        when(urunRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> urunService.updateUrun(1L, new UrunDTO()));
        assertTrue(ex.getMessage().contains("Urun bulunamadı"));
    }

    // ------------ DELETE ------------------

    @Test
    void deleteUrun_shouldDelete_whenFound() {
        Urun urun = new Urun(); urun.setId(5L);

        when(urunRepository.findById(5L)).thenReturn(Optional.of(urun));

        urunService.deleteUrun(5L);

        verify(urunRepository).delete(urun);
    }

    @Test
    void deleteUrun_shouldThrow_whenNotFound() {
        when(urunRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> urunService.deleteUrun(10L));
        assertTrue(ex.getMessage().contains("Urun bulunamadı"));
    }

    // ------------ GET ALL ------------------

    @Test
    void getAllUrun_shouldReturnPage() {
        Page<Urun> mockPage = new PageImpl<>(List.of(new Urun(), new Urun()));
        Pageable pageable = PageRequest.of(0, 10);

        when(urunRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Urun> result = urunService.getAllUrun(pageable);

        assertEquals(2, result.getContent().size());
        verify(urunRepository).findAll(pageable);
    }
}
