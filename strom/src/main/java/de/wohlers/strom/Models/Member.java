package de.wohlers.strom.Models;

import de.wohlers.strom.Entities.NotificationMethod;
import javafx.beans.property.*;

public class Member {

    private final IntegerProperty                    id;
    private final StringProperty                     title;
    private final StringProperty                     name;
    private final StringProperty                     street;
    private final StringProperty                     zip;
    private final StringProperty                     city;
    private final StringProperty                     phone;
    private final StringProperty                     email;
    private final StringProperty                     epost;
    private final BooleanProperty                    directDebit;
    private final ObjectProperty<NotificationMethod> preferredNotificationMethod;

    /*
    TODO
     - Transiente Informationen anzeigen:
       - Gemietete Plätze: - oder A01 oder A01, B02, ...

     */

    public Member() { /* TODO - Die ViewModel-Instanzen sollen aus den Entities generiert werden */
        this(null);
    }

    public Member(String name) {

        id = new SimpleIntegerProperty();
        title = new SimpleStringProperty();
        this.name = new SimpleStringProperty(name);
        street = new SimpleStringProperty();
        zip = new SimpleStringProperty();
        city = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        epost = new SimpleStringProperty();
        directDebit = new SimpleBooleanProperty();
        preferredNotificationMethod = new SimpleObjectProperty<>();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public String getZip() {
        return zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public StringProperty zipProperty() {
        return zip;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getEpost() {
        return epost.get();
    }

    public void setEpost(String epost) {
        this.epost.set(epost);
    }

    public StringProperty epostProperty() {
        return epost;
    }

    public boolean isDirectDebit() {
        return directDebit.get();
    }

    public void setDirectDebit(boolean directDebit) {
        this.directDebit.set(directDebit);
    }

    public BooleanProperty directDebitProperty() {
        return directDebit;
    }

    public NotificationMethod getPreferredNotificationMethod() {
        return preferredNotificationMethod.get();
    }

    public void setPreferredNotificationMethod(NotificationMethod preferredNotificationMethod) {
        this.preferredNotificationMethod.set(preferredNotificationMethod);
    }

    public ObjectProperty<NotificationMethod> preferredNotificationMethodProperty() {
        return preferredNotificationMethod;
    }
}
