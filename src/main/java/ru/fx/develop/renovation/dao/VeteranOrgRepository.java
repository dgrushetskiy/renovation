package ru.fx.develop.renovation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.VeteranOrg;

import java.util.List;

public interface VeteranOrgRepository extends JpaRepository<VeteranOrg, Long>, JpaSpecificationExecutor<VeteranOrg> {

    List<VeteranOrg> findByHouseIn(House house);
}
