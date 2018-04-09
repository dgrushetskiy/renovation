package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.VeteranOrg;

public interface VeteranOrgService {

    ObservableList<VeteranOrg> getByHouse(House house);
}
