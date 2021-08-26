package com.mb.bootbootique.repositories;

import com.mb.bootbootique.entities.Boot;
import com.mb.bootbootique.enums.BootType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BootRepository extends CrudRepository<Boot, Integer> {

    List<Boot> findBootsBySize(Float size);

    List<Boot> findBootsByMaterial(String material);

    List<Boot> findBootsByType(BootType type);

    List<Boot> findBootsByQuantityGreaterThan(Integer minQuantity);
}
