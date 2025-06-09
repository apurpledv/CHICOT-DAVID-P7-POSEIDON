package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;

@SpringBootTest
public class BidListServiceTest {
    @Autowired
	private BidListService bidService;

    @MockitoBean
    private BidListRepository bidRepository;

    @Test
    public void bidListServiceTest() throws Exception {
        BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		assertTrue(bidService.saveBidList(bid));

		// Update
		bid.setAccount("Account Null");
		assertTrue(bidService.saveBidList(bid));

		// Get BidLists
		assertTrue(bidService.getAllBidLists() instanceof List);

        // Get One BidList
        when(bidRepository.getReferenceById(anyInt())).thenReturn(bid);
        assertTrue(bidService.getBidListById(1) instanceof BidList);
        
		// Delete
		assertTrue(bidService.deleteBidList(1));
    }
}
