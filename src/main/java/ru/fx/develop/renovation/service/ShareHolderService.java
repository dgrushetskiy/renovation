package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.ShareHolder;


public interface ShareHolderService {

    ObservableList<ShareHolder> getByHouse(House house);
}
