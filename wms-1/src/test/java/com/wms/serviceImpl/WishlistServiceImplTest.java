package com.wms.serviceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import com.wms.repository.ProductRepository;
import com.wms.repository.UserRepository;
import com.wms.repository.WishlistRepository;

@ActiveProfiles("test")
public class WishlistServiceImplTest {

	@Test
	void addItem_Success() {
		UserRepository urepo = Mockito.mock(UserRepository.class);
		WishlistRepository wrepo= Mockito.mock(WishlistRepository.class);
		ProductRepository prepo= Mockito.mock(ProductRepository.class);
		
		
	}

}
