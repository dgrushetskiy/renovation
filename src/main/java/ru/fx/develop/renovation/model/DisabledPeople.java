package ru.fx.develop.renovation.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.fx.develop.renovation.model.fabric.HouseParameters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "disabled_people")
@Cacheable(false)
public class DisabledPeople implements HouseParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "unom", foreignKey = @ForeignKey(name = "fk_disabled_people_house"))
    private House house;
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Column(name = "patronymic", length = 100)
    private String patronymic;
    @Column(name = "birthday")
    private LocalDate birthDay;
    @Column(name = "address")
    private String address;
    @Column(name = "apartment", length = 10)
    private String apartment;
    @Column(name = "address_full")
    private String addressFull;
    @Column(name = "grupp_inv")
    private Integer groupInvalid;
    @Column(name = "armchair")
    private boolean armchair;
    @Column(name = "single")
    private boolean single;
    @Column(name = "demands", length = 512)
    private String demands;
    @Column(name = "svod", length = 100)
    private String svod;
    @Column(name = "type_age", length = 100)
    private String typeAge;
    @Column(name = "grown")
    private Integer grown;
    @Column(name = "adaptation")
    private Integer adaptation;
    @Column(name = "child")
    private Integer child;
    @Column(name = "improvement")
    private Integer improvement;
    @Column(name = "totals")
    private Integer totals;
    @Column(name = "nompp1")
    private Integer nompp1;
    @Column(name = "geocode")
    private Integer geoCode;

    public DisabledPeople() {
    }

    public DisabledPeople(String addressFull, Integer groupInvalid, boolean armchair, boolean single, Integer adaptation, Integer improvement, Integer totals) {
        this.addressFull = addressFull;
        this.groupInvalid = groupInvalid;
        this.armchair = armchair;
        this.single = single;
        this.adaptation = adaptation;
        this.improvement = improvement;
        this.totals = totals;
    }

    public DisabledPeople(House house, String firstName, String lastName, String patronymic, LocalDate birthDay, String address, String apartment, String addressFull, Integer groupInvalid, boolean armchair, boolean single, String demands, String svod, String typeAge, Integer grown, Integer adaptation, Integer child, Integer improvement, Integer totals, Integer nompp1, Integer geoCode) {
        this.house = house;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDay = birthDay;
        this.address = address;
        this.apartment = apartment;
        this.addressFull = addressFull;
        this.groupInvalid = groupInvalid;
        this.armchair = armchair;
        this.single = single;
        this.demands = demands;
        this.svod = svod;
        this.typeAge = typeAge;
        this.grown = grown;
        this.adaptation = adaptation;
        this.child = child;
        this.improvement = improvement;
        this.totals = totals;
        this.nompp1 = nompp1;
        this.geoCode = geoCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getVidPrava() {
        return null;
    }

    @Override
    public BigDecimal getSqrOForm() {
        return null;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getAddressFull() {
        return addressFull;
    }

    public void setAddressFull(String addressFull) {
        this.addressFull = addressFull;
    }

    public Integer getGroupInvalid() {
        return groupInvalid;
    }

    public void setGroupInvalid(Integer groupInvalid) {
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

    public String getDemands() {
        return demands;
    }

    public void setDemands(String demands) {
        this.demands = demands;
    }

    public String getSvod() {
        return svod;
    }

    public void setSvod(String svod) {
        this.svod = svod;
    }

    public String getTypeAge() {
        return typeAge;
    }

    public void setTypeAge(String typeAge) {
        this.typeAge = typeAge;
    }

    public Integer getGrown() {
        return grown;
    }

    public void setGrown(Integer grown) {
        this.grown = grown;
    }

    public Integer getAdaptation() {
        return adaptation;
    }

    public void setAdaptation(Integer adaptation) {
        this.adaptation = adaptation;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public Integer getImprovement() {
        return improvement;
    }

    public void setImprovement(Integer improvement) {
        this.improvement = improvement;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public Integer getNompp1() {
        return nompp1;
    }

    public void setNompp1(Integer nompp1) {
        this.nompp1 = nompp1;
    }

    public Integer getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(Integer geoCode) {
        this.geoCode = geoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof DisabledPeople) return false;
        DisabledPeople other = (DisabledPeople) o;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
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
