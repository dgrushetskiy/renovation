package ru.fx.develop.renovation.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.fx.develop.renovation.model.fabric.HouseParameters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "share_holder")
@Cacheable(false)
public class ShareHolder implements HouseParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "unom", foreignKey = @ForeignKey(name = "fk_share_holder_house"))
    private House house;
    @Column(name = "kad_nom", length = 100)
    private String kadNom;
    @Column(name = "kad_nom_prava", length = 100)
    private String kadNomPrava;
    @Column(name = "explikazia", length = 512)
    private String explikazia;
    @Column(name = "s_oform")
    private BigDecimal sqrOForm;
    @Column(name = "vid_prava", length = 100)
    private String vidPrava;
    @Column(name = "name_subject", length = 512)
    private String nameSubject;
    @Column(name = "num_egrp", length = 100)
    private String numEGRP;
    @Column(name = "dateegrp")
    private LocalDate dateEGRP;

    public ShareHolder() {
    }

    public ShareHolder(House house, BigDecimal sqrOForm, String vidPrava) {
        this.house = house;
        this.sqrOForm = sqrOForm;
        this.vidPrava = vidPrava;
    }

    public ShareHolder(House house, String kadNom, String kadNomPrava, String explikazia, BigDecimal sqrOForm, String vidPrava, String nameSubject, String numEGRP, LocalDate dateEGRP) {
        this.house = house;
        this.kadNom = kadNom;
        this.kadNomPrava = kadNomPrava;
        this.explikazia = explikazia;
        this.sqrOForm = sqrOForm;
        this.vidPrava = vidPrava;
        this.nameSubject = nameSubject;
        this.numEGRP = numEGRP;
        this.dateEGRP = dateEGRP;
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

    @Override
    public String getAddress() {
        return getHouse().getAddress();
    }

    public String getKadNom() {
        return kadNom;
    }

    public void setKadNom(String kadNom) {
        this.kadNom = kadNom;
    }

    public String getKadNomPrava() {
        return kadNomPrava;
    }

    public void setKadNomPrava(String kadNomPrava) {
        this.kadNomPrava = kadNomPrava;
    }

    public String getExplikazia() {
        return explikazia;
    }

    public void setExplikazia(String explikazia) {
        this.explikazia = explikazia;
    }

    public BigDecimal getSqrOForm() {
        return sqrOForm;
    }

    public void setSqrOForm(BigDecimal sqrOForm) {
        this.sqrOForm = sqrOForm;
    }

    public String getVidPrava() {
        return vidPrava;
    }

    public void setVidPrava(String vidPrava) {
        this.vidPrava = vidPrava;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getNumEGRP() {
        return numEGRP;
    }

    public void setNumEGRP(String numEGRP) {
        this.numEGRP = numEGRP;
    }

    public LocalDate getDateEGRP() {
        return dateEGRP;
    }

    public void setDateEGRP(LocalDate dateEGRP) {
        this.dateEGRP = dateEGRP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareHolder that = (ShareHolder) o;
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
                .toString();
    }
}
