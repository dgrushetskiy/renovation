package ru.fx.develop.renovation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.SecondaryRight;

import java.util.List;

public interface SecondaryRightRepository extends JpaRepository<SecondaryRight, Long>, JpaSpecificationExecutor<SecondaryRight> {

    List<SecondaryRight> findByHouse(House house);
}
