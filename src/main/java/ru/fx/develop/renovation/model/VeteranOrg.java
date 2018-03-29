package ru.fx.develop.renovation.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.fx.develop.renovation.model.fabric.HouseParameters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "veterans")
@Cacheable(false)
public class VeteranOrg implements HouseParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "unom", foreignKey = @ForeignKey(name = "fk_veterans_house"))
    private House house;
    @Column(name = "adress_full", length = 512)
    private String address;
    @Column(name = "pl_pom")
    private BigDecimal sqrOForm;
    @Column(name = "pravoobl")
    private String vidPrava;
    @Column(name = "document", length = 1024)
    private String document;

    public VeteranOrg() {
    }

    public VeteranOrg(House house, String address, BigDecimal sqrOForm, String vidPrava) {
        this.house = house;
        this.address = address;
        this.sqrOForm = sqrOForm;
        this.vidPrava = vidPrava;
    }

    public VeteranOrg(House house, String address, BigDecimal sqrOForm, String vidPrava, String document) {
        this.house = house;
        this.address = address;
        this.sqrOForm = sqrOForm;
        this.vidPrava = vidPrava;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adressFull) {
        this.address = address;
    }

    public BigDecimal getSqrOForm() {
        return sqrOForm;
    }

    public void setSqrOForm(BigDecimal plPom) {
        this.sqrOForm = plPom;
    }

    public String getVidPrava() {
        return vidPrava;
    }

    public void setVidPrava(String vidPrava) {
        this.vidPrava = vidPrava;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VeteranOrg that = (VeteranOrg) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(house, that.house);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("house", this.house)
                .append("vidPrava", this.vidPrava)
                .append("sqrOForm", this.sqrOForm)
                .append("document", this.document)
                .toString();
    }
}
