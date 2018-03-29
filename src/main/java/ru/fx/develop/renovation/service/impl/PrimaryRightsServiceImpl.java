package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fx.develop.renovation.dao.PrimaryRightRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;
import ru.fx.develop.renovation.service.PrimaryRightsService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PrimaryRightsServiceImpl implements PrimaryRightsService {
    @Autowired
    private PrimaryRightRepository primaryRightRepository;

//    @Override
//    public ObservableList<PrimaryRight> findHouseAddressVidPravaSqrOForm(String address, String vidPrava, BigDecimal sqrOForm) {
//        return FXCollections.observableArrayList(Lists.newArrayList(
//                primaryRightRepository.findByHouse_AddressAndVidPravaAndSqrOForm(address,vidPrava,sqrOForm)
//        ));
//    }
//    @Override
//    public ObservableList<PrimaryRight> getPrimaryRightHouse(Long unom) {
//        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.primaryRightHouse(unom)));
//    }

//    @Override
//    public ObservableList<PrimaryRight> getAll() {
//        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.findAll()));
//    }



    @Override
    //@Transactional
    public ObservableList<PrimaryRight> selectAll(Long unom) {
        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.selectAll(unom)));
    }

    @Override
    public ObservableList<PrimaryRight> getByHouse(House house) {
        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.findByHouseIn(house)));
    }

    @Override
    public ObservableList<PrimaryRight> getAll() {
        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.findAll()));
    }

    @Override
  // @javax.transaction.Transactional
    public ObservableList<PrimaryRight> getHouseAddressVidPravaSqrOForm(Long unom, String address, String vidPrava, BigDecimal sqrOForm) {
        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.selectUnom(unom,address,vidPrava,sqrOForm)));
    }

    @Override
    public ObservableList<PrimaryRight> getByUnomAndAddress(Long unom) {
        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.findByUnomAndAddress(unom)));
    }
}
