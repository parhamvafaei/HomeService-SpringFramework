package com.maktab.base.service.impl;

import com.maktab.base.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

//why base Entity
public interface BaseService<E extends BaseEntity> {
    Long saveOrUpdate(E e);

    void delete(E e);

    Optional<E> findById(Long id);

    List<E> findAll();

    boolean isExistsById(Long id);

}
