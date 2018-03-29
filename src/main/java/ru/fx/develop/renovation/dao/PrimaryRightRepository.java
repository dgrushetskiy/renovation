package ru.fx.develop.renovation.dao;

import javafx.collections.ObservableList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PrimaryRightRepository extends JpaRepository<PrimaryRight, Long>, JpaSpecificationExecutor<PrimaryRight>{
//    @Query(value = "SELECT h.address, pr.vid_prava,pr.s_oform FROM house AS h, primary_rights AS pr WHERE h.unom=pr.unom", nativeQuery = true)
//    List<PrimaryRight> primaryRightHouse(@Param("unom") Long unom);
    //@Query(value = "SELECT h. FROM House", nativeQuery = true)

    @Query(value = "SELECT h.address, pr.vid_prava,pr.s_oform FROM House AS h, primary_rights AS pr WHERE h.unom=pr.unom AND h.unom IN (:unom)", nativeQuery = true)
    List<PrimaryRight> selectAll(@Param(value = "unom") Long unom);

    List<PrimaryRight> findByHouseIn(House house);

    @Query(
            value = "select h.address,p.vid_prava, p.s_oform from primary_rights as p, house as h where h.unom = :unom " +
                    "and h.address = :address " +
                    "and p.vid_prava = :vidPrava " +
                    "and p.s_oform = :sqrOForm",
            nativeQuery = true
    )
   List<PrimaryRight> selectUnom(
            @Param("unom") Long unom,
           @Param("address") String address,
           @Param("vidPrava") String vidPrava,
           @Param("sqrOForm") BigDecimal sqrOForm);//findByHouse_AddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm)

    @Query(value = "SELECT h.unom , h.ao,h.kad_nom, h.mr, h.address, " +
            "primaryRight.id, primaryRight.kad_nom, primaryRight.unom,primaryRight.kad_nom_prava,primaryRight.explikazia, primaryRight.vid_prava, primaryRight.s_oform, primaryRight.name_subject " +
            " FROM house h JOIN primary_rights AS primaryRight  ON primaryRight.unom = :unom", nativeQuery = true)
    List<PrimaryRight> findByUnomAndAddress(@Param("unom")Long unom);
}
