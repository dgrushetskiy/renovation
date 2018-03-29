package ru.fx.develop.renovation.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class VeteranOrgDTO {

    private HouseDTO houseDTO;
    private String vidPrava;
    private BigDecimal sqrOForm;

    public VeteranOrgDTO() {
    }

    public HouseDTO getHouseDTO() {
        return houseDTO;
    }

    public void setHouseDTO(HouseDTO houseDTO) {
        this.houseDTO = houseDTO;
    }

    public String getVidPrava() {
        return vidPrava;
    }

    public void setVidPrava(String vidPrava) {
        this.vidPrava = vidPrava;
    }

    public BigDecimal getSqrOForm() {
        return sqrOForm;
    }

    public void setSqrOForm(BigDecimal sqrOForm) {
        this.sqrOForm = sqrOForm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("house", this.houseDTO)
                .append("vidPrava", this.vidPrava)
                .append("sqrOForm", this.sqrOForm)
                .toString();
    }
}
