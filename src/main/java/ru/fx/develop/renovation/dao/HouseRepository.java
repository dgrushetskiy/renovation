package ru.fx.develop.renovation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.fx.develop.renovation.model.House;


import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

    public final static String house_primary_right = "SELECT h.address, pr.vid_prava, pr.s_oform FROM house h JOIN primary_rights pr ON h.unom = pr.unom";

    House findByUnom(Long unom);

    //Optional<House> findOne(Long unom);
    //@Transactional
    @Query(value = "SELECT h.unom , h.ao,h.kad_nom, h.mr, h.address, primaryRight.vid_prava, primaryRight.s_oform  FROM house h JOIN primary_rights AS primaryRight  ON primaryRight.unom = :unom", nativeQuery = true)
    List<House> findByUnomAndAddress(@Param("unom")Long unom);

    List<House> findAllByUnom(Long unom);

    List<House> findDistinctByUnomInOrderByUnom(Long unom);

    Page<House> findDistinctByUnomInOrderByUnom(Long unom, Pageable pageable);

    List<House> findDistinctByMrContainingIgnoreCase(String mr);

    Page<House> findDistinctByMrContainingIgnoreCase(String mr, Pageable pageable);

    List<House> findDistinctByAddressContainingIgnoreCase(String address);//GroupByAddress

    Page<House> findDistinctByAddressContainingIgnoreCase(String adress, Pageable pageable);//GroupByAddress

    //List<House> findDistinctByPrimaryRightSetContainingIg_VidPrava_SqrOForm_BigTenPowersTableMax()
}
