package com.nnk.springboot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    RuleNameController ruleNameController;

    @MockitoBean 
    RuleNameService ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    public void setup() {
        ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    }

    @Test
    public void ruleNameControllerTest() throws Exception {
        // List View
        this.mockMvc.perform(get("/ruleName/list"))
            .andExpect(status().isOk());

        // Add View
        this.mockMvc.perform(get("/ruleName/add"))
            .andExpect(status().isOk());

        // Add Action
        when(ruleNameService.saveRuleName(any(RuleName.class))).thenReturn(true);
        this.mockMvc.perform(post("/ruleName/validate")
            .flashAttr("ruleName", ruleName))
            .andExpect(status().isFound());

        // Update View
        when(ruleNameService.getRuleNameById(anyInt())).thenReturn(ruleName);
        this.mockMvc.perform(get("/ruleName/update/2"))
            .andExpect(status().isOk());

        // Update Action
        ruleName.setName("New Rule Name");
        this.mockMvc.perform(post("/ruleName/update/3")
            .flashAttr("ruleName", ruleName))
            .andExpect(status().isFound());

        // Delete Action
        this.mockMvc.perform(get("/ruleName/delete/3"))
            .andExpect(status().isFound());
    }

    @Test
    public void ruleNameControllerNonValidTest() throws Exception {
        // List View Non Valid
        when(ruleNameService.getAllRuleNames()).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/ruleName/list"))
            .andExpect(status().isOk());

        // Add Action Non Valid
        this.mockMvc.perform(post("/ruleName/validate"))
            .andExpect(status().isOk());

        when(ruleNameService.saveRuleName(any(RuleName.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(post("/ruleName/validate")
            .flashAttr("ruleName", ruleName))
            .andExpect(status().isFound());

        // Update View Non Valid
        when(ruleNameService.getRuleNameById(any(int.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/ruleName/update/-1"))
            .andExpect(status().isFound());

        // Update Action Non Valid
        this.mockMvc.perform(post("/ruleName/update/3"))
            .andExpect(status().isFound());

        this.mockMvc.perform(post("/ruleName/update/3")
            .flashAttr("ruleName", ruleName))
            .andExpect(status().isFound());

        // Delete Action Non Valid
        this.mockMvc.perform(get("/ruleName/delete/3"))
            .andExpect(status().isFound());

        when(ruleNameService.deleteRuleName(anyInt())).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/ruleName/delete/3"))
            .andExpect(status().isFound());
    }
}
