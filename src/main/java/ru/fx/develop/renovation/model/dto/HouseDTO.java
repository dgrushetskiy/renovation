package ru.fx.develop.renovation.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Component;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.PrimaryRight;
import ru.fx.develop.renovation.model.VeteranOrg;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class HouseDTO implements Serializable{

    private Long unom;
    private String mr;
    private String address;
    private Set<PrimaryRightDTO> primaryRightSet = new HashSet<>();
    private Set<VeteranOrg> veteranOrgSet = new HashSet<>();
    private Set<DisabledPeople> disabledPeopleSet = new HashSet<>();

    public HouseDTO() {
    }

    public Long getUnom() {
        return unom;
    }

    public void setUnom(Long unom) {
        this.unom = unom;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<PrimaryRightDTO> getPrimaryRightSet() {
        return primaryRightSet;
    }

    public void setPrimaryRightSet(Set<PrimaryRightDTO> primaryRightSet) {
        this.primaryRightSet = primaryRightSet;
    }

    public Set<VeteranOrg> getVeteranOrgSet() {
        return veteranOrgSet;
    }

    public void setVeteranOrgSet(Set<VeteranOrg> veteranOrgSet) {
        this.veteranOrgSet = veteranOrgSet;
    }

    public Set<DisabledPeople> getDisabledPeopleSet() {
        return disabledPeopleSet;
    }

    public void setDisabledPeopleSet(Set<DisabledPeople> disabledPeopleSet) {
        this.disabledPeopleSet = disabledPeopleSet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("unom", this.unom)
                .append("address", this.address)
                .append("raion", this.mr)
                .toString();
    }
}
