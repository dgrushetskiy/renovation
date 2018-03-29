package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.SecondaryRight;

import java.math.BigDecimal;
import java.util.List;


public interface SecondaryRightService {

    ObservableList<SecondaryRight> getByHouse(House house);

    ObservableList<SecondaryRight> getAll();

    ObservableList<SecondaryRight> getByHouseAndAddressAndVidPravaAndSqrOForm(House house, String address, String vidPrava, BigDecimal sqrOForm);
}
