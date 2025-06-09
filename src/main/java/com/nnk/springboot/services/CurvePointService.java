package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
    @Autowired
    CurvePointRepository curvePointRepo;

    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepo.findAll();
    }

    public CurvePoint getCurvePointById(int id) {
        return curvePointRepo.getReferenceById(id);
    }

    public boolean saveCurvePoint(CurvePoint curvePoint) {
        curvePointRepo.save(curvePoint);
        return true;
    }

    public boolean deleteCurvePoint(int id) {
        curvePointRepo.delete(getCurvePointById(id));
        return true;
    }
}
