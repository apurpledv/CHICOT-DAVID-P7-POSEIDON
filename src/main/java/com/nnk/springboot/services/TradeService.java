package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepo;

    public List<Trade> getAllTrades() {
        return tradeRepo.findAll();
    }

    public Trade getTradeById(int id) {
        return tradeRepo.getReferenceById(id);
    }

    public boolean saveTrade(Trade trade) {
        tradeRepo.save(trade);
        return true;
    }

    public boolean deleteTrade(int id) {
        tradeRepo.delete(getTradeById(id));
        return true;
    }
}
