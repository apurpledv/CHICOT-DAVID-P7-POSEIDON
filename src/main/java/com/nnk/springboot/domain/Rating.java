package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    
    @NotBlank(message = "MoodysRating is mandatory")
    String moodysRating;
    
    @NotBlank(message = "SandPRating is mandatory")
    String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    String fitchRating;
    
    @NotNull(message = "OrderNumber is mandatory")
    Integer orderNumber;

    public Rating() {
        
    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public boolean isValid() {
        return (this.moodysRating != null && this.sandPRating != null && this.fitchRating != null && this.orderNumber != null);
    }
}
