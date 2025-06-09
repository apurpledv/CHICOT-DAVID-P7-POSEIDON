package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;

@SpringBootTest
public class RuleNameServiceTest {
    @Autowired
	private RuleNameService ruleNameService;

    @MockitoBean
    private RuleNameRepository ruleNameRepository;

    @Test
    public void ratingServiceTest() throws Exception {
        RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		assertTrue(ruleNameService.saveRuleName(ruleName));

		// Update
		ruleName.setName("New Rule Name");
		assertTrue(ruleNameService.saveRuleName(ruleName));

		// Get RuleNames
		assertTrue(ruleNameService.getAllRuleNames() instanceof List);

        // Get One RuleName
        when(ruleNameRepository.getReferenceById(anyInt())).thenReturn(ruleName);
        assertTrue(ruleNameService.getRuleNameById(1) instanceof RuleName);
        
		// Delete
		assertTrue(ruleNameService.deleteRuleName(1));
    }
}
