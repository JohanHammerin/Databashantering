package se.johan.jdbclab.v48project;

public class House {
    //Attributes
    private int houseId;
    private String address;
    private String city;
    private String postalCode;
    private double price;
    private int personId;

    //Constructors
    House(int houseId, String address, String city, String postalCode, double price, int personId) {
        setHouseId(houseId);
        setAddress(address);
        setCity(city);
        setPostalCode(postalCode);
        setPrice(price);
        setPersonId(personId);
    }

    House(String address, String city, String postalCode, double price, int personId) {
        setAddress(address);
        setCity(city);
        setPostalCode(postalCode);
        setPrice(price);
        setPersonId(personId);
    }


    //Getters & Setters
    public int getHouseId() {
        return this.houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPersonId() {
        return this.personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
