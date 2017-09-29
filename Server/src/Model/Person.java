package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    private String surname;
    private String name;
    private String fathername;
    private String country;
    private String region;
    private String city;
    private String street;
    private int house;
    private int housing;
    private int apartment;

    public void setData(ArrayList<String> arrayList) {
        surname = arrayList.get(0);
        name = arrayList.get(1);
        fathername = arrayList.get(2);
        country = arrayList.get(3);
        region = arrayList.get(4);
        city = arrayList.get(5);
        street = arrayList.get(6);
        house = Integer.parseInt(arrayList.get(7));
        housing = Integer.parseInt(arrayList.get(8));
        apartment = Integer.parseInt(arrayList.get(9));
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getFathername() {
        return fathername;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse() {
        return house;
    }

    public int getHousing() {
        return housing;
    }

    public int getApartment() {
        return apartment;
    }
}