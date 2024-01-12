package com.den.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

import com.den.entity.listener.StudentLisTener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(metamodel = @Metamodel, listener = StudentLisTener.class)
@Table(name = "student")
@Data
@NoArgsConstructor
public class _student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String code;

  @Column(name = "clazz_id")
  private Long clazzId;

  private Date birthday;

  private String address;

  private String email;

  private String phone;

  private int status;

  private String image;
}
