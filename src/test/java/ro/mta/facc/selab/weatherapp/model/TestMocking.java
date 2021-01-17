package ro.mta.facc.selab.weatherapp.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;

class TestMocking {

    String cityName;
    String countryCode;

    private Model testMocking;
    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        BufferedReader var=null;
        var=mock(BufferedReader.class);

        when(var.readLine()).thenReturn("Hjo");
        cityName=var.readLine();
        when(var.readLine()).thenReturn("SE");
        countryCode=var.readLine();
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
            testMocking=new Model(delim[1],delim[4]);

        }


    }

    @org.junit.jupiter.api.Test
    void getCountryCode() {
        assertEquals(testMocking.getCountryCode(),countryCode);

    }

    @org.junit.jupiter.api.Test
    void getCityName() {
        assertEquals(testMocking.getCityName(), cityName);
    }
}