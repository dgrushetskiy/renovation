package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.dao.VeteranOrgRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.VeteranOrg;
import ru.fx.develop.renovation.service.VeteranOrgService;

@Service
public class VeteranOrgServiceImpl implements VeteranOrgService {
    @Autowired
    private VeteranOrgRepository veteranOrgRepository;

    @Override
    public ObservableList<VeteranOrg> getByHouse(House house) {
        return FXCollections.observableArrayList(Lists.newArrayList(veteranOrgRepository.findByHouseIn(house)));
    }
}
