package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;

@SpringBootTest
public class CurvePointServiceTest {
    @Autowired
	private CurvePointService curveService;

    @MockitoBean
    private CurvePointRepository curveRepository;

    @Test
    public void curvePointServiceTest() throws Exception {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		assertTrue(curveService.saveCurvePoint(curvePoint));

		// Update
		curvePoint.setTerm(18d);
		assertTrue(curveService.saveCurvePoint(curvePoint));

		// Get Curve Points
		assertTrue(curveService.getAllCurvePoints() instanceof List);

        // Get One Curve Point
        when(curveRepository.getReferenceById(anyInt())).thenReturn(curvePoint);
        assertTrue(curveService.getCurvePointById(1) instanceof CurvePoint);
        
		// Delete
		assertTrue(curveService.deleteCurvePoint(10));
    }
}
