package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.dao.HouseRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.service.HouseService;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Override
    public ObservableList<House> getAddress(String address) {
        return FXCollections.observableArrayList(Lists.newArrayList(houseRepository.findDistinctByAddressContainingIgnoreCase(address)));
    }

    @Override
    public Page getAll(int from, int count) {
        return houseRepository.findAll(new PageRequest(from, count, Sort.Direction.ASC, "mr"));
    }

    @Override
    public Page getAll(int from, int count, String... text) {
        return houseRepository.findDistinctByMrContainingIgnoreCase(text[0], new PageRequest(from, count, Sort.Direction.ASC, "mr"));
    }

    @Override
    public Page getAllUnom(int from, int count) {
        return houseRepository.findAll(new PageRequest(from, count, Sort.Direction.ASC, "unom"));
    }

    @Override
    public Page getAllUnom(int from, int count, Long... unom) {
        return houseRepository.findDistinctByUnomInOrderByUnom(unom[0], new PageRequest(from, count, Sort.Direction.ASC, "unom"));
    }

    @Override
    public Page getAllAddress(int from, int count) {
        return houseRepository.findAll(new PageRequest(from, count, Sort.Direction.ASC, "address"));
    }

    @Override
    public Page getAllAddress(int from, int count, String... address) {
        return houseRepository.findDistinctByAddressContainingIgnoreCase(address[0], new PageRequest(from, count, Sort.Direction.ASC, "address"));
    }
}
