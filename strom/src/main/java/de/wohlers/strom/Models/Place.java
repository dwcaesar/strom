package de.wohlers.strom.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@SuppressWarnings ("UnsupportedTypeWithoutConverterInspection")
@javax.persistence.Entity
@Access (AccessType.PROPERTY)
public class Place {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private       Long           id;
    private final StringProperty name = new SimpleStringProperty();

    /*
    TODO
     - Transiente Informationen anzeigen:
       - aktueller Mieter: - oder Member.name
       - aktueller Stromliefervertrag: - oder Contract.startDate
       - aktuell zugeordneter Stromzähler: - oder Meter.identifier
     */

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Access (AccessType.PROPERTY)
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
