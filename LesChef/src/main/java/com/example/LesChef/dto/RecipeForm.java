package com.example.LesChef.dto;

import com.example.LesChef.entity.Customer;
import com.example.LesChef.entity.Recipe;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeForm {
    private Long recipeId;
    private String recipeName;
    private Long viewNum;
    private Date writeDate;
    private String runTime;
    private String portion;
    private String cookLevel;
    private Customer userId;
    private String recipeImg;
    private String majorCategory;
    private String subCategory;
    private Double ratingAvg;

    public Recipe toEntity(){
        return Recipe.builder()
                .recipeName(this.recipeName)
                .viewNum(0L)
                .writeDate(Date.valueOf(LocalDate.now()))
                .runTime(this.runTime)
                .portion(this.portion)
                .cookLevel(this.cookLevel)
                .userId(this.userId)
                .recipeImg(this.recipeImg)
                .majorCategory("공유")
                .subCategory(null)
                .ratingAvg(this.ratingAvg != null ? this.ratingAvg : 0)
                .build();
    }
}
