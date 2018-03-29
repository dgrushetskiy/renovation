package ru.fx.develop.renovation.service.impl;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.fx.develop.renovation.dao.HouseRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.service.HouseService;

@Service
public class HouseServiceImpl implements HouseService{
    @Autowired
    private HouseRepository houseRepository;

    @Override
    public House getHouseUnom(Long unom) {
        return houseRepository.findByUnom(unom);
    }

    @Override
   // @Transactional
    public ObservableList<House> getByUnomAndAddress(Long unom) {
        return FXCollections.observableArrayList(Lists.newArrayList(houseRepository.findByUnomAndAddress(unom)));
    }

    @Override
    public ObservableList<House> getAll() {
        return FXCollections.observableArrayList(Lists.newArrayList(houseRepository.findAll()));
    }

    @Override
    public ObservableList<House> getUnom(Long unom) {
        return FXCollections.observableArrayList(Lists.newArrayList(houseRepository.findDistinctByUnomInOrderByUnom(unom)));
    }

    @Override
    public ObservableList<House> getMr(String mr) {
        return FXCollections.observableArrayList(Lists.newArrayList(houseRepository.findDistinctByMrContainingIgnoreCase(mr)));
    }

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
    public Page getAllMr(int from, int count) {
        return houseRepository.findAll(new PageRequest(from, count, Sort.Direction.ASC, "mr"));
    }

    @Override
    public Page getAllMr(int from, int count, String... mr) {
        return houseRepository.findDistinctByMrContainingIgnoreCase(mr[0], new PageRequest(from, count, Sort.Direction.ASC, "mr"));
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
