package com.jsbcrud.www.repository;

import com.jsbcrud.www.model.Thing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThingRepository extends JpaRepository<Thing, Long> {
    List<Thing> findByStatusOrderByDateDesc(Thing.Status status);

    Optional<Thing> findByIdAndStatus(Integer id, Thing.Status status);

}