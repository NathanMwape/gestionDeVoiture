package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Voiture.
 */
@Entity
@Table(name = "voiture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Voiture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "marque")
    private String marque;

    @Column(name = "modele")
    private String modele;

    @Column(name = "description")
    private String description;

    @Column(name = "disponibilite")
    private Boolean disponibilite;

    @Column(name = "tarif_journalier")
    private Double tarifJournalier;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservations", "voitures" }, allowSetters = true)
    private GestionFlotte gestionFlotte;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Voiture id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return this.marque;
    }

    public Voiture marque(String marque) {
        this.setMarque(marque);
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return this.modele;
    }

    public Voiture modele(String modele) {
        this.setModele(modele);
        return this;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getDescription() {
        return this.description;
    }

    public Voiture description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisponibilite() {
        return this.disponibilite;
    }

    public Voiture disponibilite(Boolean disponibilite) {
        this.setDisponibilite(disponibilite);
        return this;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Double getTarifJournalier() {
        return this.tarifJournalier;
    }

    public Voiture tarifJournalier(Double tarifJournalier) {
        this.setTarifJournalier(tarifJournalier);
        return this;
    }

    public void setTarifJournalier(Double tarifJournalier) {
        this.tarifJournalier = tarifJournalier;
    }

    public GestionFlotte getGestionFlotte() {
        return this.gestionFlotte;
    }

    public void setGestionFlotte(GestionFlotte gestionFlotte) {
        this.gestionFlotte = gestionFlotte;
    }

    public Voiture gestionFlotte(GestionFlotte gestionFlotte) {
        this.setGestionFlotte(gestionFlotte);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voiture)) {
            return false;
        }
        return id != null && id.equals(((Voiture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voiture{" +
            "id=" + getId() +
            ", marque='" + getMarque() + "'" +
            ", modele='" + getModele() + "'" +
            ", description='" + getDescription() + "'" +
            ", disponibilite='" + getDisponibilite() + "'" +
            ", tarifJournalier=" + getTarifJournalier() +
            "}";
    }
}
