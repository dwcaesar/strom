package de.wohlers.strom.Models;

import javafx.beans.property.*;

import javax.persistence.*;

@SuppressWarnings ("UnsupportedTypeWithoutConverterInspection")
@Entity (name = "Club")
@Access (AccessType.PROPERTY)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private       Long           id;
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty street = new SimpleStringProperty();
    private final StringProperty zip = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final DoubleProperty basicFeeRate = new SimpleDoubleProperty();
    private final DoubleProperty connectFee = new SimpleDoubleProperty();
    private final DoubleProperty powerRate = new SimpleDoubleProperty();
    private final ObjectProperty<Member> contact = new SimpleObjectProperty<>();
    private final ObjectProperty<Member> treasurer = new SimpleObjectProperty<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Access (AccessType.PROPERTY)
    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    @Access (AccessType.PROPERTY)
    public String getZip() {
        return zip.get();
    }

    public StringProperty zipProperty() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    @Access (AccessType.PROPERTY)
    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    @Access (AccessType.PROPERTY)
    public double getBasicFeeRate() {
        return basicFeeRate.get();
    }

    public DoubleProperty basicFeeRateProperty() {
        return basicFeeRate;
    }

    public void setBasicFeeRate(double basicFeeRate) {
        this.basicFeeRate.set(basicFeeRate);
    }

    @Access (AccessType.PROPERTY)
    public double getConnectFee() {
        return connectFee.get();
    }

    public DoubleProperty connectFeeProperty() {
        return connectFee;
    }

    public void setConnectFee(double connectFee) {
        this.connectFee.set(connectFee);
    }

    @Access (AccessType.PROPERTY)
    public double getPowerRate() {
        return powerRate.get();
    }

    public DoubleProperty powerRateProperty() {
        return powerRate;
    }

    public void setPowerRate(double powerRate) {
        this.powerRate.set(powerRate);
    }

    @Access (AccessType.PROPERTY)
    @ManyToOne (fetch = FetchType.LAZY)
    public Member getContact() {
        return contact.get();
    }

    public ObjectProperty<Member> contactProperty() {
        return contact;
    }

    public void setContact(Member contact) {
        this.contact.set(contact);
    }

    @Access (AccessType.PROPERTY)
    @ManyToOne (fetch = FetchType.LAZY)
    public Member getTreasurer() {
        return treasurer.get();
    }

    public ObjectProperty<Member> treasurerProperty() {
        return treasurer;
    }

    public void setTreasurer(Member treasurer) {
        this.treasurer.set(treasurer);
    }
}
