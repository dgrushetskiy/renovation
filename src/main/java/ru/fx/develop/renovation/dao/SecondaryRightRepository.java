package ru.fx.develop.renovation.dao;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.SecondaryRight;

import java.math.BigDecimal;
import java.util.List;

public interface SecondaryRightRepository extends JpaRepository<SecondaryRight, Long>,JpaSpecificationExecutor<SecondaryRight> {
//    @Query(value = "SELECT h.address, sr.vid_prava,sr.vid_prava_red, sr.s_oform FROM house AS h, secondary_rights AS sr WHERE h.unom=sr.unom", nativeQuery = true)
//    List<SecondaryRight> secondaryRightHouse(@Param("unom") Long unom);

    List<SecondaryRight> findByHouse(House house);

    List<SecondaryRight> findByHouse_AddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm);//findByHouseAndAddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm)
}
