package ru.fx.develop.renovation.model.fabric;

import ru.fx.develop.renovation.model.House;

import java.io.Serializable;
import java.math.BigDecimal;

public interface HouseParameters extends Serializable {
    House getHouse();

    String getAddress();

    String getVidPrava();

    BigDecimal getSqrOForm();
}
