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

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    CurvePointController curvePointController;

    @MockitoBean 
    CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setup() {
        curvePoint = new CurvePoint(10, 10d, 30d);
    }

    @Test
    public void curvePointControllerTest() throws Exception {
        // List View
        this.mockMvc.perform(get("/curvePoint/list"))
            .andExpect(status().isOk());

        // Add View
        this.mockMvc.perform(get("/curvePoint/add"))
            .andExpect(status().isOk());

        // Add Action
        when(curvePointService.saveCurvePoint(any(CurvePoint.class))).thenReturn(true);
        this.mockMvc.perform(post("/curvePoint/validate")
            .flashAttr("curvePoint", curvePoint))
            .andExpect(status().isFound());

        // Update View
        when(curvePointService.getCurvePointById(anyInt())).thenReturn(curvePoint);
        this.mockMvc.perform(get("/curvePoint/update/2"))
            .andExpect(status().isOk());

        // Update Action
        curvePoint.setTerm(15d);
        this.mockMvc.perform(post("/curvePoint/update/3")
            .flashAttr("curvePoint", curvePoint))
            .andExpect(status().isFound());

        // Delete Action
        this.mockMvc.perform(get("/curvePoint/delete/3"))
            .andExpect(status().isFound());
    }

    @Test
    public void curvePointControllerNonValidTest() throws Exception {
        // List View Non Valid
        when(curvePointService.getAllCurvePoints()).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/curvePoint/list"))
            .andExpect(status().isOk());

        // Add Action Non Valid
        this.mockMvc.perform(post("/curvePoint/validate"))
            .andExpect(status().isOk());

        when(curvePointService.saveCurvePoint(any(CurvePoint.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(post("/curvePoint/validate")
            .flashAttr("curvePoint", curvePoint))
            .andExpect(status().isFound());

        // Update View Non Valid
        when(curvePointService.getCurvePointById(any(int.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/curvePoint/update/-1"))
            .andExpect(status().isFound());

        // Update Action Non Valid
        this.mockMvc.perform(post("/curvePoint/update/3"))
            .andExpect(status().isFound());

        this.mockMvc.perform(post("/curvePoint/update/3")
            .flashAttr("curvePoint", curvePoint))
            .andExpect(status().isFound());

        // Delete Action Non Valid
        this.mockMvc.perform(get("/curvePoint/delete/3"))
            .andExpect(status().isFound());

        when(curvePointService.deleteCurvePoint(anyInt())).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/curvePoint/delete/3"))
            .andExpect(status().isFound());
    }
}
