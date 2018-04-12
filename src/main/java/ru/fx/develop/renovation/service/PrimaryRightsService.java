package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;


public interface PrimaryRightsService {

    ObservableList<PrimaryRight> getByHouse(House house);

    ObservableList<PrimaryRight> getByHouseInAndVidPrava(House house);
}
