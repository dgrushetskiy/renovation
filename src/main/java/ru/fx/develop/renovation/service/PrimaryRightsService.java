package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import org.springframework.data.repository.query.Param;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;

import java.math.BigDecimal;
import java.util.List;


public interface PrimaryRightsService {

  //ObservableList<PrimaryRight> getPrimaryRightHouse(Long unom);

   //ObservableList<PrimaryRight> getAll();

   ObservableList<PrimaryRight> selectAll(Long unom);

   ObservableList<PrimaryRight> getByHouse(House house);

   ObservableList<PrimaryRight> getAll();

   ObservableList<PrimaryRight> getHouseAddressVidPravaSqrOForm(Long unom,String address, String vidPrava, BigDecimal sqrOForm);

   ObservableList<PrimaryRight> getByUnomAndAddress(Long unom);
}
