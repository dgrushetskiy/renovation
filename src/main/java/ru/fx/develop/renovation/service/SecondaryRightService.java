package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.SecondaryRight;


public interface SecondaryRightService {

    ObservableList<SecondaryRight> getByHouse(House house);
}
