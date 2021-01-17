package ro.mta.facc.selab.weatherapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clasa care gestioneaza informatiile legate de orase
 * @author Ana Ungureanu
 * */

public class Model {
    public StringProperty cityID;
    public StringProperty countryCode;
    public StringProperty cityName;

    /**
     * Constructorul pentru clasa Model
     *  cityName=numele orasului
     *  countrycode=codul tarii in care se afla orasul
     *  */
    public Model(String cityName, String countryCode) {
        this.cityName = new SimpleStringProperty(cityName);
        this.countryCode = new SimpleStringProperty(countryCode);
    }


    public String getCountryCode() { return countryCode.get(); }
    public void setCountryCode(String country_code){ this.countryCode.set(country_code); }


    public String getCityId() { return cityID.get(); }
    public void setCityID(String city_id) { this.cityID.set(city_id); }

    public String getCityName(){ return cityName.get(); }
    public void setCityName(String city_name){ this.cityName.set(city_name); }



}
