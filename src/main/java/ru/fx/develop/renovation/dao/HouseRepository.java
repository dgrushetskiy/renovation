package ru.fx.develop.renovation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.fx.develop.renovation.model.House;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

    Page<House> findDistinctByUnomInOrderByUnom(Long unom, Pageable pageable);

    Page<House> findDistinctByMrContainingIgnoreCase(String mr, Pageable pageable);

    List<House> findDistinctByAddressContainingIgnoreCase(String address);//GroupByAddress

    Page<House> findDistinctByAddressContainingIgnoreCase(String adress, Pageable pageable);//GroupByAddress
}
