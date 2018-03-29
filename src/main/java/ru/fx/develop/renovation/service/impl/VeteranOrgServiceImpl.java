package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.dao.VeteranOrgRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.VeteranOrg;
import ru.fx.develop.renovation.service.VeteranOrgService;

import javax.validation.constraints.Max;
import java.math.BigDecimal;

@Service
public class VeteranOrgServiceImpl implements VeteranOrgService{
    @Autowired
    private VeteranOrgRepository veteranOrgRepository;
//    @Override
//    public ObservableList<VeteranOrg> getVeteranOrgHouse(Long unom) {
//        return FXCollections.observableArrayList(Lists.newArrayList(veteranOrgRepository.veteranOrgHouse(unom)));
//    }

    @Override
    public ObservableList<VeteranOrg> getByHouse(House house) {
        return FXCollections.observableArrayList(Lists.newArrayList(veteranOrgRepository.findByHouseIn(house)));
    }

    @Override
    public ObservableList<VeteranOrg> getAll() {
        return FXCollections.observableArrayList(Lists.newArrayList(veteranOrgRepository.findAll()));
    }

    @Override
    public ObservableList<VeteranOrg> getByHouseAndAddressAndVidPravaAndSqrOForm(House house, String address, String vidPrava, BigDecimal sqrOForm) {
        return FXCollections.observableArrayList(Lists.newArrayList(veteranOrgRepository.findByHouseAndAddressAndVidPravaAndSqrOForm(house, address, vidPrava, sqrOForm)));
    }

    @Override
    public ObservableList<VeteranOrg> getByHouseInAndAddressAndVidPravaAndSqrOForm(House house, String address, String vidPrava, BigDecimal sqrOForm) {
        return FXCollections.observableArrayList(Lists.newArrayList(veteranOrgRepository.findByHouseInAndAddressAndVidPravaAndSqrOForm(house, address, vidPrava, sqrOForm)));
    }
}
