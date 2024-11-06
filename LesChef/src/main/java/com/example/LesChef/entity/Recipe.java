package com.example.LesChef.entity;

import com.example.LesChef.dto.RecipeForm;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id_seq")
    @SequenceGenerator(name = "recipe_id_seq", sequenceName = "recipe_id_seq", initialValue = 500, allocationSize = 1)
    private Long recipeId;
    @Column(nullable = false)
    private String recipeName;
    @Column(nullable = false)
    private Long viewNum;
    @Column(nullable = false)
    private Date writeDate;
    @Column(nullable = false)
    private String runTime;
    @Column(nullable = false)
    private String portion;
    @Column(nullable = false)
    private String cookLevel;

    @ManyToOne
    @JoinColumn(name = "nickname", referencedColumnName = "nickName")
    private Customer userId;
    @Column(nullable = false)
    private String recipeImg;
    @Column(nullable = false)
    private String majorCategory;
    private String subCategory;
    private Double ratingAvg;

    @Builder
    public Recipe(String recipeName, Long viewNum, Date writeDate,
                  String runTime,String portion, String cookLevel,
                  Customer userId,String recipeImg, String majorCategory,String subCategory, Double ratingAvg){
        this.recipeName = recipeName;
        this.viewNum = viewNum;
        this.writeDate = writeDate;
        this.runTime = runTime;
        this.portion = portion;
        this.cookLevel = cookLevel;
        this.userId = userId;
        this.recipeImg = recipeImg;
        this.majorCategory = majorCategory;
        this.subCategory = subCategory;
        this.ratingAvg = ratingAvg;
    }

    public RecipeForm toForm(){
    return new RecipeForm(this.recipeId,this.recipeName,this.viewNum,
            this.writeDate, this.runTime, this.cookLevel, this.userId, this.recipeImg,
            this.majorCategory,this.subCategory, this.ratingAvg);
    }

 }
