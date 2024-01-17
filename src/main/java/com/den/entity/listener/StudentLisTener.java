package com.den.entity.listener;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

import com.den.entity._student;

public class StudentLisTener implements EntityListener<_student> {

  @Override
  public void preUpdate(_student entity, PreUpdateContext<_student> context) {
    updateCode(entity);
  }

  @Override
  public void postInsert(_student entity, PostInsertContext<_student> context) {
    entity.setCode("PS" + String.format("%04d", entity.getId()));

  }

  private void updateCode(_student entity) {
    entity.setCode("PS" + String.format("%04d", entity.getId()));
  }
}