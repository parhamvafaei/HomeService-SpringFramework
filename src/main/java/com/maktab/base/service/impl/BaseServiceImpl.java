package com.maktab.base.service.impl;


import com.maktab.base.entity.BaseEntity;
import com.maktab.base.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<E extends BaseEntity, R extends JpaRepository<E, Long>> implements BaseService<E> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Long saveOrUpdate(E e) {
        repository.save(e);
        return e.getId();
    }


    @Override
    public void delete(E e) {
        repository.delete(e);
    }

    @Override
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean isExistsById(Long id) {
        return repository.existsById(id);

    }
}