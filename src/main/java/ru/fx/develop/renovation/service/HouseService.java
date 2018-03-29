package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import org.springframework.data.domain.Page;
import ru.fx.develop.renovation.model.House;

public interface HouseService {

    House getHouseUnom(Long unom);

    ObservableList<House> getByUnomAndAddress(Long unom);

    ObservableList<House> getAll();

    ObservableList<House> getUnom(Long unom);

    ObservableList<House> getMr(String mr);

    ObservableList<House> getAddress(String address);

    Page getAll(int from, int count);

    Page getAll(int from, int count, String... text);

    Page getAllUnom(int from, int count);

    Page getAllUnom(int from, int count, Long... unom);

    Page getAllMr(int from, int count);

    Page getAllMr(int from, int count, String... mr);

    Page getAllAddress(int from, int count);

    Page getAllAddress(int from, int count, String... address);
}
