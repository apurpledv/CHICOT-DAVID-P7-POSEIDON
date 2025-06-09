package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    
    String name;
    String description;
    String json;
    String template;
    String sqlStr;
    String sqlPart;

    public RuleName() {
        
    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public boolean isValid() {
        return (this.name != null && this.description != null && this.json != null && this.template != null && this.sqlStr != null && this.sqlPart != null);
    }
}
