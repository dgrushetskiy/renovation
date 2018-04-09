package ru.fx.develop.renovation.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.fx.develop.renovation.model.fabric.HouseParameters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "secondary_rights")
@Cacheable(false)
public class SecondaryRight implements HouseParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "unom", foreignKey = @ForeignKey(name = "fk_secondary_rights_house"))
    private House house;
    @Column(name = "kad_nom", length = 100)
    private String kadNom;
    @Column(name = "kad_nom_prava", length = 100)
    private String kadNomPrava;
    @Column(name = "s_oform")
    private BigDecimal sqrOForm;
    @Column(name = "vid_prava", length = 100)
    private String vidPrava;
    @Column(name = "vid_prava_red", length = 100)
    private String vidPravaRed;
    @Column(name = "name_subject", length = 512)
    private String nameSubject;
    @Column(name = "num_dogovor", length = 100)
    private String numDogovor;
    @Column(name = "date_dogovor")
    private LocalDate dateDogovor;
    @Column(name = "date_end_dogovor")
    private LocalDate dateEndDogovor;
    @Column(name = "num_egrp", length = 100)
    private String numEGRP;
    @Column(name = "dateegrp")
    private LocalDate dateEGRP;
    @Column(name = "note", length = 512)
    private String note;

    public SecondaryRight() {
    }

    public SecondaryRight(House house, BigDecimal sqrOForm, String vidPrava, String vidPravaRed) {
        this.house = house;
        this.sqrOForm = sqrOForm;
        this.vidPrava = vidPrava;
        this.vidPravaRed = vidPravaRed;
    }

    public SecondaryRight(House house, String kadNom, String kadNomPrava, BigDecimal sqrOForm, String vidPrava, String vidPravaRed, String nameSubject, String numDogovor, LocalDate dateDogovor, LocalDate dateEndDogovor, String numEGRP, LocalDate dateEGRP, String note) {
        this.house = house;
        this.kadNom = kadNom;
        this.kadNomPrava = kadNomPrava;
        this.sqrOForm = sqrOForm;
        this.vidPrava = vidPrava;
        this.vidPravaRed = vidPravaRed;
        this.nameSubject = nameSubject;
        this.numDogovor = numDogovor;
        this.dateDogovor = dateDogovor;
        this.dateEndDogovor = dateEndDogovor;
        this.numEGRP = numEGRP;
        this.dateEGRP = dateEGRP;
        this.note = note;
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

    public String getVidPravaRed() {
        return vidPravaRed;
    }

    public void setVidPravaRed(String vidPravaRed) {
        this.vidPravaRed = vidPravaRed;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getNumDogovor() {
        return numDogovor;
    }

    public void setNumDogovor(String numDogovor) {
        this.numDogovor = numDogovor;
    }

    public LocalDate getDateDogovor() {
        return dateDogovor;
    }

    public void setDateDogovor(LocalDate dateDogovor) {
        this.dateDogovor = dateDogovor;
    }

    public LocalDate getDateEndDogovor() {
        return dateEndDogovor;
    }

    public void setDateEndDogovor(LocalDate dateEndDogovor) {
        this.dateEndDogovor = dateEndDogovor;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondaryRight that = (SecondaryRight) o;
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
