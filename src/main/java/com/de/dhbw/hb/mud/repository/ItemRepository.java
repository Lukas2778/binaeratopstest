package com.de.dhbw.hb.mud.repository;

import com.de.dhbw.hb.mud.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Override
    List<Item> findAll();
}
