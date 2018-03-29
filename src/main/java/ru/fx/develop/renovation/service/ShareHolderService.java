package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.ShareHolder;

import java.math.BigDecimal;
import java.util.List;


public interface ShareHolderService {

   // ObservableList<ShareHolder> getShareHolderHouse(Long unom);

    ObservableList<ShareHolder> getByHouse(House house);

    ObservableList<ShareHolder> getAll();

    ObservableList<ShareHolder> getByHouseAndAddressAndVidPravaAndSqrOForm(House house,String address, String vidPrava, BigDecimal sqrOForm);
}
