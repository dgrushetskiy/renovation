package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.dao.ShareHolderRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.ShareHolder;
import ru.fx.develop.renovation.service.ShareHolderService;

import java.math.BigDecimal;

@Service
public class ShareHolderServiceImpl implements ShareHolderService {
    @Autowired
    private ShareHolderRepository shareHolderRepository;
//    @Override
//    public ObservableList<ShareHolder> getShareHolderHouse(Long unom) {
//        return  FXCollections.observableArrayList(Lists.newArrayList(shareHolderRepository.shareHolderHouse(unom)));
//    }

    @Override
    public ObservableList<ShareHolder> getByHouse(House house) {
        return FXCollections.observableArrayList(Lists.newArrayList(shareHolderRepository.findByHouse(house)));
    }

    @Override
    public ObservableList<ShareHolder> getAll() {
        return FXCollections.observableArrayList(Lists.newArrayList(shareHolderRepository.findAll()));
    }

    @Override
    public ObservableList<ShareHolder> getByHouseAndAddressAndVidPravaAndSqrOForm(House house, String address, String vidPrava, BigDecimal sqrOForm) {
        return FXCollections.observableArrayList(Lists.newArrayList(shareHolderRepository.findByHouse_AddressAndVidPravaAndSqrOForm(house, address, vidPrava, sqrOForm)));
    }
}
