package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    
    @NotBlank(message = "Name is mandatory")
    String name;
    
    @NotBlank(message = "Description is mandatory")
    String description;
    
    @NotBlank(message = "JSON is mandatory")
    String json;
    
    @NotBlank(message = "Template is mandatory")
    String template;
    
    @NotBlank(message = "SQLStr is mandatory")
    String sqlStr;
    
    @NotBlank(message = "SQLPart is mandatory")
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
