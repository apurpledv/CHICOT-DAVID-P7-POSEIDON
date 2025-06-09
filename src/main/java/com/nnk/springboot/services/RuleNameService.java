package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {
    @Autowired
    RuleNameRepository ruleNameRepo;

    public List<RuleName> getAllRuleNames() {
        return ruleNameRepo.findAll();
    }

    public RuleName getRuleNameById(int id) {
        return ruleNameRepo.getReferenceById(id);
    }

    public boolean saveRuleName(RuleName rating) {
        ruleNameRepo.save(rating);
        return true;
    }

    public boolean deleteRuleName(int id) {
        ruleNameRepo.delete(getRuleNameById(id));
        return true;
    }
}
