package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.dao.SecondaryRightRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.SecondaryRight;
import ru.fx.develop.renovation.service.SecondaryRightService;

@Service
public class SecondaryRightServiceImpl implements SecondaryRightService {
    @Autowired
    private SecondaryRightRepository secondaryRightRepository;

    @Override
    public ObservableList<SecondaryRight> getByHouse(House house) {
        return FXCollections.observableArrayList(Lists.newArrayList(secondaryRightRepository.findByHouse(house)));
    }
}
