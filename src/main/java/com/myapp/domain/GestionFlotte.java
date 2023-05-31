package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GestionFlotte.
 */
@Entity
@Table(name = "gestion_flotte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GestionFlotte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "gestionFlotte")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur", "voiture", "gestionFlotte" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "gestionFlotte")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "gestionFlotte" }, allowSetters = true)
    private Set<Voiture> voitures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GestionFlotte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.setGestionFlotte(null));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.setGestionFlotte(this));
        }
        this.reservations = reservations;
    }

    public GestionFlotte reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public GestionFlotte addReservations(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setGestionFlotte(this);
        return this;
    }

    public GestionFlotte removeReservations(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setGestionFlotte(null);
        return this;
    }

    public Set<Voiture> getVoitures() {
        return this.voitures;
    }

    public void setVoitures(Set<Voiture> voitures) {
        if (this.voitures != null) {
            this.voitures.forEach(i -> i.setGestionFlotte(null));
        }
        if (voitures != null) {
            voitures.forEach(i -> i.setGestionFlotte(this));
        }
        this.voitures = voitures;
    }

    public GestionFlotte voitures(Set<Voiture> voitures) {
        this.setVoitures(voitures);
        return this;
    }

    public GestionFlotte addVoitures(Voiture voiture) {
        this.voitures.add(voiture);
        voiture.setGestionFlotte(this);
        return this;
    }

    public GestionFlotte removeVoitures(Voiture voiture) {
        this.voitures.remove(voiture);
        voiture.setGestionFlotte(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestionFlotte)) {
            return false;
        }
        return id != null && id.equals(((GestionFlotte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestionFlotte{" +
            "id=" + getId() +
            "}";
    }
}
