package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.dao.DisabledPeopleRepository;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.service.DisabledPeopleService;

@Service
public class DisabledPeopleServiceImpl implements DisabledPeopleService{
    @Autowired
    private DisabledPeopleRepository disabledPeopleRepository;

    @Override
    public ObservableList<DisabledPeople> getByHouse(House house) {
        return FXCollections.observableArrayList(Lists.newArrayList(disabledPeopleRepository.findByHouseIn(house)));
    }

    @Override
    public ObservableList<DisabledPeople> getAll() {
        return FXCollections.observableArrayList(Lists.newArrayList(disabledPeopleRepository.findAll()));
    }

    @Override
    public ObservableList<DisabledPeople> getByHouseAndAddressAndGroupInvalidAndArmchairAndSingleAndImprovementAndAdaptationAndTotals(House house, String address, int groupInvalid, boolean armchair, boolean single, int improvement, int adaptation, int totals) {
        return FXCollections.observableArrayList(Lists.newArrayList(disabledPeopleRepository.findByHouseAndAddressAndGroupInvalidAndArmchairAndSingleAndImprovementAndAdaptationAndTotals(house,address,groupInvalid,armchair,single,improvement,adaptation,totals)));
    }
}
