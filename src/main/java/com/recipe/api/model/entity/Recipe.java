package com.recipe.api.model.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.recipe.api.enums.RecipeType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** This class is a database entity responsible for holding recipe information. */
@Entity
@Table(name = "recipe")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Recipe {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String name;

  @Column private String instruction;

  @NotBlank
  @Column(nullable = false)
  private String variant = RecipeType.VEGETARIAN.name();

  @Column(name = "serving_count")
  private Integer servingCount;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "recipe_ingredient",
      joinColumns = @JoinColumn(name = "recipe_id"),
      inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
  private Set<Ingredient> ingredients = new HashSet<>();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Recipe(
      String name,
      String instruction,
      String variant,
      Integer servingCount,
      Set<Ingredient> ingredients) {
    this.name = name;
    this.instruction = instruction;
    this.variant = variant;
    this.servingCount = servingCount;
    this.ingredients = ingredients;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }

  public Integer getServingCount() {
    return servingCount;
  }

  public void setServingCount(Integer servingCount) {
    this.servingCount = servingCount;
  }

  public Set<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(Set<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
