package ru.fx.develop.renovation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.ShareHolder;

import java.math.BigDecimal;
import java.util.List;

public interface ShareHolderRepository extends JpaRepository<ShareHolder, Long>, JpaSpecificationExecutor<ShareHolder> {
//    @Query(value = "SELECT h.address,sh.vid_prava, sh.s_oform FROM house AS h, share_holder AS sh WHERE h.unom=sh.unom;", nativeQuery = true)
//    List<ShareHolder> shareHolderHouse(@Param("unom") Long unom);

    List<ShareHolder> findByHouse(House house);

    List<ShareHolder> findByHouse_AddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm);//findByHouseAndAddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm)
}
