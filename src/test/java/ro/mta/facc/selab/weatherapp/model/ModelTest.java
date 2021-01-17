package ro.mta.facc.selab.weatherapp.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    String cityName;
    String countryCode;

    private Model test;
    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        cityName="Hjo";
        countryCode="SE";
        BufferedReader citiesFromFile= new BufferedReader(new FileReader("src/main/resources/input"));
        String line=citiesFromFile.readLine();
        List<String > list=new ArrayList<String>();
        while(line!=null)
        {
            list.add(line);
            line=citiesFromFile.readLine();

        }
        for(String line1:list)
        {
            String [] delim=line1.split(":");
            test=new Model(delim[1],delim[4]);

        }


    }

    @org.junit.jupiter.api.Test
    void getCountryCode() {
        assertEquals(test.getCountryCode(),countryCode);

    }

    @org.junit.jupiter.api.Test
    void getCityName() {
        assertEquals(test.getCityName(), cityName);
    }
}