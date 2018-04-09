package ru.fx.develop.renovation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.ShareHolder;

import java.util.List;

public interface ShareHolderRepository extends JpaRepository<ShareHolder, Long>, JpaSpecificationExecutor<ShareHolder> {

    List<ShareHolder> findByHouse(House house);
}
