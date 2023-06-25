package com.recipe.api.model.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** This class is an database entity responsible for holding ingredient information. */
@Entity
@DynamicUpdate
@Table(name = "ingredient")
@EntityListeners(AuditingEntityListener.class)
public class Ingredient implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String name;

  @Column(updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column @UpdateTimestamp private LocalDateTime updatedAt;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "ingredients")
  @JsonIgnoreProperties
  private Set<Recipe> recipes;

  public Ingredient(String name, Set<Recipe> recipes) {
    this.name = name;
    this.recipes = recipes;
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

  public Set<Recipe> getRecipes() {
    return recipes;
  }

  public void setRecipes(Set<Recipe> recipes) {
    this.recipes = recipes;
  }
}
