package de.wohlers.strom.Models;

import javafx.beans.property.*;

import javax.persistence.*;

@SuppressWarnings ("UnsupportedTypeWithoutConverterInspection")
@Entity (name = "Member")
@Access (AccessType.PROPERTY)
public class Member {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private       Long                               id;
    private final StringProperty                     title                       = new SimpleStringProperty();
    private final StringProperty                     name                        = new SimpleStringProperty();
    private final StringProperty                     street                      = new SimpleStringProperty();
    private final StringProperty                     zip                         = new SimpleStringProperty();
    private final StringProperty                     city                        = new SimpleStringProperty();
    private final StringProperty                     phone                       = new SimpleStringProperty();
    private final StringProperty                     email                       = new SimpleStringProperty();
    private final StringProperty                     epost                       = new SimpleStringProperty();
    private final BooleanProperty                    directDebit                 = new SimpleBooleanProperty();
    private final ObjectProperty<NotificationMethod> preferredNotificationMethod = new SimpleObjectProperty<>();

    /*
    TODO
     - Transiente Informationen anzeigen:
       - Gemietete Plätze: - oder A01 oder A01, B02, ...

     */

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Access (AccessType.PROPERTY)
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    @Access (AccessType.PROPERTY)
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Access (AccessType.PROPERTY)
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    @Access (AccessType.PROPERTY)
    public String getZip() {
        return zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public StringProperty zipProperty() {
        return zip;
    }

    @Access (AccessType.PROPERTY)
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    @Access (AccessType.PROPERTY)
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    @Access (AccessType.PROPERTY)
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    @Access (AccessType.PROPERTY)
    public String getEpost() {
        return epost.get();
    }

    public void setEpost(String epost) {
        this.epost.set(epost);
    }

    public StringProperty epostProperty() {
        return epost;
    }

    @Access (AccessType.PROPERTY)
    public boolean isDirectDebit() {
        return directDebit.get();
    }

    public void setDirectDebit(boolean directDebit) {
        this.directDebit.set(directDebit);
    }

    public BooleanProperty directDebitProperty() {
        return directDebit;
    }

    @Access (AccessType.PROPERTY)
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
