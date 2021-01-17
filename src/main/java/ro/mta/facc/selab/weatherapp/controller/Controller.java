package ro.mta.facc.selab.weatherapp.controller;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ro.mta.facc.selab.weatherapp.model.Model;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.awt.*;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import java.awt.event.*;
import org.json.simple.JSONObject;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.util.Date;


/**
 * Clasa care implementeaza functionalitatile aplicatiei folosind datele din Mode
 * @author Ana Ungureanu
 * */

public class Controller {

    @FXML
    private Label temp;
    @FXML
    private Label windSp;
    @FXML
    private Label Oras;
    @FXML
    private Label Precipitatii;
    @FXML
    private Label Timp;

    @FXML
    private Label Descriere;
    @FXML
    private ComboBox id_tara;
    @FXML
    private ComboBox id_oras;

    @FXML
    MouseEvent e;
    private ObservableList<Model> meteoData = FXCollections.observableArrayList();


    /**
     * Metoda care citeste datele din fisier si le pune in lista meteoData
     * meteoData-lista care contine un obiect de tip Model(Oras) cu toate atributele acestuia
     * citiesFromFile-contine contintul fisierului
     * delim[1]-Numele orasului din fisier
     * delim[4]-Codul tarii in care se afla orasul din fisier
     * */
    @FXML
    private void initialize() throws IOException {
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
            meteoData.add(new Model(delim[1],delim[4]));

        }


    }

    /**
     * Metoda care adauga tarile din lista meteoData in ComboBox
     * taraOnce-lista care contine o tara doar o singura data
     **/

    public void taraC(javafx.scene.input.MouseEvent mouseEvent) {
        ArrayList<String> tara=new ArrayList<>();


        for(Model it: meteoData)
        {
            tara.add(it.getCountryCode());
        }
        ArrayList<String> taraOnce=new ArrayList<>(new HashSet<>(tara));
        if(id_tara.getValue()==null)
        {
            id_tara.getItems().addAll(taraOnce);
        }
    }


    /**
     * Metoda care pune in ComboBox orasele din tara selectata
     * id_tara-ne ajuta sa obtinem tara selectata din primul ComboBox
     * oras-lista cu orasele din tara selectata
     * */
    public void orasC(javafx.scene.input.MouseEvent mouseEvent) {
        id_oras.getItems().clear();
        ArrayList<String> oras=new ArrayList<>();
        for(Model it: meteoData) {
            if (it.getCountryCode().equals(id_tara.getValue())) {
                oras.add(it.getCityName());

            }

        }

        if(id_oras.getValue()==null)
        {
            id_oras.getItems().addAll(oras);
        }

    }


    /**
     * Metoda care intoarce un obiect de tip JSON si preia
     * temperatura, precipitatiile si viteza vantului
     * Afiseaza valorile precizate in label-urile corespunzatoare
     * */
    public void cauta(javafx.scene.input.MouseEvent mouseEvent) throws IOException, ParseException, JSONException {
        //conexiune cu URL
        URL Details = null;
        Details = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + id_oras.getValue() + "," + id_tara.getValue() + "&APPID=4ed1caf805ec305e22146912cf0dc061&lang=ro&units=metric");
        URLConnection getDetails = null;
        getDetails = Details.openConnection();
        BufferedReader contet = null;
        contet = new BufferedReader(new InputStreamReader(getDetails.getInputStream()));
        String contetToString = null;
        contetToString = IOUtils.toString(contet);

        Oras.setText((String) id_oras.getValue());

        //ZonedDateTime timeInCity=ZonedDateTime.now(ZoneId.of("Europe/"+id_oras.getValue()));
        // Timp.setText(String.valueOf(timeInCity));

        // ZoneId zone1 = ZoneId.of("Europe/"+id_oras.getValue());
        // LocalTime now1 = LocalTime.now(zone1);
        // Timp.setText(String.valueOf(now1));

        Object obiect = new JSONParser().parse(contetToString);
        JSONObject returnJson = (JSONObject) obiect;

        JSONObject mainAttributes = (JSONObject) returnJson.get("main");
        String temperature = mainAttributes.get("temp").toString();
        temp.setText(temperature);

        String humidity = mainAttributes.get("humidity").toString();
        Precipitatii.setText(humidity);

        JSONObject wind = (JSONObject) returnJson.get("wind");
        String speedWind = wind.get("speed").toString();
        windSp.setText(speedWind);


        JSONArray weath = (JSONArray) returnJson.get("weather");
        for (int i = 0; i <= weath.size(); i++) {
            JSONObject details = (JSONObject) weath.get(i);
            String descriereVreme = details.get("description").toString();
            //JSONObject weather=(JSONObject) returnJson.get("weather");
            Descriere.setText(descriereVreme);

        }
    }
}

