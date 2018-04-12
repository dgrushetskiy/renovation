package ru.fx.develop.renovation.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "house")
@Cacheable(false)
public class House implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false, unique = true)
//    private Long id;
    @Column(name = "unom", nullable = false, unique = true)
    private Long unom;
    @Column(name = "ao", length = 10)
    private String ao;
    @Column(name = "mr", length = 20)
    private String mr;
    @Column(name = "address")
    private String address;
    @Column(name = "kad_nom", length = 100)
    private String kadNom;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PrimaryRight> primaryRightSet = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SecondaryRight> secondaryRightSet = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShareHolder> shareHolderSet = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VeteranOrg> veteranOrgSet = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DisabledPeople> disabledPeopleSet = new HashSet<>();

    public House() {
    }

    public House(String address) {
        this.address = address;
    }

    public House(Long unom, String address) {
        this.unom = unom;
        this.address = address;
    }

    public House(Long unom, String address, Set<PrimaryRight> primaryRightSet, Set<SecondaryRight> secondaryRightSet, Set<ShareHolder> shareHolderSet, Set<VeteranOrg> veteranOrgSet, Set<DisabledPeople> disabledPeopleSet) {
        this.unom = unom;
        this.address = address;
        this.primaryRightSet = primaryRightSet;
        this.secondaryRightSet = secondaryRightSet;
        this.shareHolderSet = shareHolderSet;
        this.veteranOrgSet = veteranOrgSet;
        this.disabledPeopleSet = disabledPeopleSet;
    }

    public House(Long unom, String mr, String address) {
        this.unom = unom;
        this.mr = mr;
        this.address = address;
    }

    public House(Long unom, String ao, String mr, String address, String kadNom) {

        this.unom = unom;
        this.ao = ao;
        this.mr = mr;
        this.address = address;
        this.kadNom = kadNom;
    }

    public House(Long unom, String mr, String address, Set<PrimaryRight> primaryRightSet, Set<SecondaryRight> secondaryRightSet, Set<ShareHolder> shareHolderSet, Set<VeteranOrg> veteranOrgSet, Set<DisabledPeople> disabledPeopleSet) {
        this.unom = unom;
        this.mr = mr;
        this.address = address;
        this.primaryRightSet = primaryRightSet;
        this.secondaryRightSet = secondaryRightSet;
        this.shareHolderSet = shareHolderSet;
        this.veteranOrgSet = veteranOrgSet;
        this.disabledPeopleSet = disabledPeopleSet;
    }


    public House(Long unom, String ao, String mr, String address, String kadNom, Set<PrimaryRight> primaryRightSet, Set<SecondaryRight> secondaryRightSet, Set<ShareHolder> shareHolderSet, Set<VeteranOrg> veteranOrgSet, Set<DisabledPeople> disabledPeopleSet) {
        this.unom = unom;
        this.ao = ao;
        this.mr = mr;
        this.address = address;
        this.kadNom = kadNom;
        this.primaryRightSet = primaryRightSet;
        this.secondaryRightSet = secondaryRightSet;
        this.shareHolderSet = shareHolderSet;
        this.veteranOrgSet = veteranOrgSet;
        this.disabledPeopleSet = disabledPeopleSet;
    }

    public Long getUnom() {
        return unom;
    }

    public void setUnom(Long unom) {
        this.unom = unom;
    }

    public String getAo() {
        return ao;
    }

    public void setAo(String ao) {
        this.ao = ao;
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

    public String getKadNom() {
        return kadNom;
    }

    public void setKadNom(String kadNom) {
        this.kadNom = kadNom;
    }

    public Set<PrimaryRight> getPrimaryRightSet() {
        return primaryRightSet;
    }

    public void setPrimaryRightSet(Set<PrimaryRight> primaryRightSet) {
        this.primaryRightSet = primaryRightSet;
    }

    public void addPrimaryRights(PrimaryRight primaryRight) {
        primaryRight.setHouse(this);
        primaryRightSet.add(primaryRight);
    }

    public Set<SecondaryRight> getSecondaryRightSet() {
        return secondaryRightSet;
    }

    public void setSecondaryRightSet(Set<SecondaryRight> secondaryRightSet) {
        this.secondaryRightSet = secondaryRightSet;
    }

    public Set<ShareHolder> getShareHolderSet() {
        return shareHolderSet;
    }

    public void setShareHolderSet(Set<ShareHolder> shareHolderSet) {
        this.shareHolderSet = shareHolderSet;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) {
            return false;
        }
        House other = (House) o;
        if (unom != null) {
            if (!unom.equals(other.unom)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((unom == null) ? 0 : unom.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("unom", this.unom)
                .append("address", this.address)
                .append("raion", this.mr)
                .toString();
//        String result = getClass().getSimpleName() + " ";
//        if (address != null && !address.trim().isEmpty()) result +="address: " + address + "\n";
//        return result;
    }
}
