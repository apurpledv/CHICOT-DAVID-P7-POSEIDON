package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeid")
    Integer id;

    @NotBlank(message = "Account is mandatory")
    String account;

    @NotBlank(message = "Type is mandatory")
    String type;

    @NotNull(message = "BuyQuantity is mandatory")
    Double buyQuantity;

    Double sellQuantity;
    Double buyPrice;
    Double sellPrice;
    String benchmark;
    Timestamp tradeDate;
    String security;
    String status;
    String trader;
    String book;
    String creationName;
    Timestamp creationDate;
    String revisionName;
    Timestamp revisionDate;
    String dealName;
    String dealType;
    String sourceListId;
    String side;

    public Trade() {
        
    }

    public Trade(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    public boolean isValid() {
        return (this.account != null && this.type != null && this.buyQuantity != null);
    }
}
