package ru.fx.develop.renovation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;
import ru.fx.develop.renovation.model.SecondaryRight;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public interface PrimaryRightRepository extends JpaRepository<PrimaryRight, Long>, JpaSpecificationExecutor<PrimaryRight> {


    List<PrimaryRight> findByHouseIn(House house);

    @Query(value = "SELECT sum(s_oform) AS sum FROM house AS h, primary_rights AS pr WHERE h.unom=pr.unom AND  h.unom IN (:house)", nativeQuery = true)
    BigDecimal getSumSqrOForm(@Param(value = "house") House house);


    @Query(value = "SELECT DISTINCT h.unom, " +
            "h.address , " +
            "pr.id," +
            "pr.unom," +
            "pr.kad_nom," +
            "pr.kad_nom_prava," +
            "pr.explikazia," +
            "pr.vid_prava , " +
            "pr.name_subject , " +
            "sr.id," +
            "sr.vid_prava , " +
            "sr.vid_prava_red , " +
            "sr.name_subject , " +
            "pr.s_oform \n" +
            "FROM house AS h INNER JOIN primary_rights pr ON h.unom = pr.unom AND h.unom IN (:house) INNER JOIN secondary_rights sr ON h.unom = sr.unom ORDER BY h.unom", nativeQuery = true)
    List<PrimaryRight> houseVidPrava(@Param(value = "house") House house);
}
