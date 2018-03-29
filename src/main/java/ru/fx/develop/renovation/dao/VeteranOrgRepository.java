package ru.fx.develop.renovation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.VeteranOrg;

import java.math.BigDecimal;
import java.util.List;

public interface VeteranOrgRepository extends JpaRepository<VeteranOrg, Long>, JpaSpecificationExecutor<VeteranOrg>{
//    @Query(value = "SELECT v.adress_full, v.pravoobl, v.pl_pom FROM house AS h, veterans AS v WHERE h.unom=v.unom;", nativeQuery = true)
//    List<VeteranOrg> veteranOrgHouse(@Param("unom") Long unom);

    List<VeteranOrg> findByHouseIn(House house);

    List<VeteranOrg> findByHouseAndAddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm);

    List<VeteranOrg> findByHouseInAndAddressAndVidPravaAndSqrOForm(House house, String address, String vidPrava, BigDecimal sqrOForm);
}
