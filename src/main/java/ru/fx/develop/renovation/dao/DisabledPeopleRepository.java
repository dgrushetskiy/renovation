package ru.fx.develop.renovation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.House;

import java.util.List;

public interface DisabledPeopleRepository extends JpaRepository<DisabledPeople, Long>, JpaSpecificationExecutor<DisabledPeople> {

    List<DisabledPeople> findByHouseIn(House house);
}
