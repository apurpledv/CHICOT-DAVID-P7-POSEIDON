package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    
    Integer curveId;
    Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    Double term;
    
    @NotNull(message = "Value is mandatory")
    Double value;
    Timestamp creationDate;

    public CurvePoint() {
        
    }

    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public boolean isValid() {
        return (this.term != null && this.value != null);
    }
}
