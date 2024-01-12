package com.den.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(metamodel = @Metamodel)
@Table(name = "clazz")
@Data
@NoArgsConstructor
public class _clazz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;

  @Column(name = "school_id")
  private Long schoolId;

  private boolean status = true;

  @Column(name = "date_start")
  private Date dateStart;

  @Column(name = "date_end")
  private Date dateEnd;

}
