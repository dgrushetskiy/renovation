package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fx.develop.renovation.dao.PrimaryRightRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;
import ru.fx.develop.renovation.service.PrimaryRightsService;

@Service
@Transactional(readOnly = true)
public class PrimaryRightsServiceImpl implements PrimaryRightsService {
    @Autowired
    private PrimaryRightRepository primaryRightRepository;

    @Override
    public ObservableList<PrimaryRight> getByHouse(House house) {
        return FXCollections.observableArrayList(Lists.newArrayList(primaryRightRepository.findByHouseIn(house)));
    }
}
