package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RsEventRepository extends CrudRepository<RsEventEntity,Integer> {
    List<RsEventEntity> findAll();
}
