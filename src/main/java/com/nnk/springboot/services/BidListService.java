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

    public BidList getBidListById(int id) {
        return bidRepo.getReferenceById(id);
    }

    public boolean saveBidList(BidList bidList) {
        bidRepo.save(bidList);
        return true;
    }

    public boolean deleteBidList(int id) {
        bidRepo.delete(getBidListById(id));
        return true;
    }
}
