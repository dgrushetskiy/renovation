package ru.fx.develop.renovation.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.fx.develop.renovation.model.House;

public class DisabledPeopleDTO {

    private HouseDTO houseDto;
    private String addressFull;
    private int groupInvalid;
    private boolean armchair;
    private boolean single;
    private int adaptation;
    private int improvement;
    private int totals;

    public DisabledPeopleDTO() {
    }

    public HouseDTO getHouseDto() {
        return houseDto;
    }

    public void setHouseDto(HouseDTO houseDto) {
        this.houseDto = houseDto;
    }

    public String getAddressFull() {
        return addressFull;
    }

    public void setAddressFull(String addressFull) {
        this.addressFull = addressFull;
    }

    public int getGroupInvalid() {
        return groupInvalid;
    }

    public void setGroupInvalid(int groupInvalid) {
        this.groupInvalid = groupInvalid;
    }

    public boolean isArmchair() {
        return armchair;
    }

    public void setArmchair(boolean armchair) {
        this.armchair = armchair;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public int getAdaptation() {
        return adaptation;
    }

    public void setAdaptation(int adaptation) {
        this.adaptation = adaptation;
    }

    public int getImprovement() {
        return improvement;
    }

    public void setImprovement(int improvement) {
        this.improvement = improvement;
    }

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("house", this.houseDto)
                .append("address", this.addressFull)
                .append("groupInvalid", this.groupInvalid)
                .append("kreslo-kalaska", this.armchair)
                .append("single", this.single)
                .append("uluch_usloviya", this.improvement)
                .append("prisposobleniya", this.adaptation)
                .append("count totals invalid", this.totals)
                .toString();
    }
}
