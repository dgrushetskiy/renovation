package ru.fx.develop.renovation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;

import java.math.BigDecimal;
import java.util.List;

public interface PrimaryRightRepository extends JpaRepository<PrimaryRight, Long>, JpaSpecificationExecutor<PrimaryRight> {

    List<PrimaryRight> findByHouseIn(House house);

    @Query(value = "SELECT sum(s_oform) AS sum FROM house AS h, primary_rights AS pr WHERE h.unom=pr.unom AND  h.unom IN (:house)", nativeQuery = true)
    BigDecimal getSumSqrOForm(@Param(value = "house") House house);
}
