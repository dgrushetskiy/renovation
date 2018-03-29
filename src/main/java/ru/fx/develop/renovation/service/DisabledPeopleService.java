package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.House;

public interface DisabledPeopleService {

    ObservableList<DisabledPeople> getByHouse(House house);

    ObservableList<DisabledPeople> getAll();

    ObservableList<DisabledPeople> getByHouseAndAddressAndGroupInvalidAndArmchairAndSingleAndImprovementAndAdaptationAndTotals(House house, String address, int groupInvalid, boolean armchair, boolean single, int improvement, int adaptation,int totals);
}
