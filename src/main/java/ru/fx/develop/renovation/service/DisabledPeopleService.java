package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.House;

public interface DisabledPeopleService {

    ObservableList<DisabledPeople> getByHouse(House house);
}
