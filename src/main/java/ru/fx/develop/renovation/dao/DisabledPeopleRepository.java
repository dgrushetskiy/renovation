package ru.fx.develop.renovation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.House;

import java.util.List;

public interface DisabledPeopleRepository extends JpaRepository<DisabledPeople, Long>, JpaSpecificationExecutor<DisabledPeople> {
//    @Query(value = "SELECT dp.address_full, " +
//            "dp.grupp_inv, dp.armchair, dp.single, " +
//            "dp.improvement, dp.adaptation, dp.totals " +
//            "FROM house AS h, disabled_people AS dp WHERE h.unom=dp.unom;", nativeQuery = true)
//    List<DisabledPeople> disabledPeopleHouse(@Param("unom") Long unom);

    List<DisabledPeople> findByHouseIn(House house);

    List<DisabledPeople> findByHouse(House house);

    List<DisabledPeople> findByHouseAndAddressAndGroupInvalidAndArmchairAndSingleAndImprovementAndAdaptationAndTotals(House house, String address, int groupInvalid, boolean armchair, boolean single, int improvement, int adaptation,int totals);
}
