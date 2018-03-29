package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.VeteranOrg;

import java.math.BigDecimal;
import java.util.List;

public interface VeteranOrgService {

    //ObservableList<VeteranOrg> getVeteranOrgHouse(Long unom);

    ObservableList<VeteranOrg> getByHouse(House house);

    ObservableList<VeteranOrg> getAll();

    ObservableList<VeteranOrg> getByHouseAndAddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm);

    ObservableList<VeteranOrg> getByHouseInAndAddressAndVidPravaAndSqrOForm(House house, String address, String vidPrava, BigDecimal sqrOForm);
}
