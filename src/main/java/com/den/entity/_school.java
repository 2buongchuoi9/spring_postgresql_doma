package com.den.entity;

import org.seasar.doma.Dao;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(metamodel = @Metamodel)
@Table(name = "school")
@Data
@NoArgsConstructor
public class _school {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String adrress;

  private String email;

  private String logo;

}
