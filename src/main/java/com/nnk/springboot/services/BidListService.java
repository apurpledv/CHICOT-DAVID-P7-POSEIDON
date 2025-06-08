package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {
    @Autowired
    BidListRepository bidRepo;

    public List<BidList> getAllBidLists() {
        return bidRepo.findAll();
    }

    public boolean addBidList(BidList bidList) {
        bidRepo.save(bidList);
        return true;
    }

    public boolean updateBidList(BidList bidList) {
        bidRepo.save(bidList);
        return true;
    }

    public boolean deleteBidList(BidList bidList) {
        bidRepo.delete(bidList);
        return true;
    }
}
