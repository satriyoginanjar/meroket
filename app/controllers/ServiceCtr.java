package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Klpd;
import models.Penyedia;

import java.util.List;

public class ServiceCtr extends Application{

    public static void index (){
        session.put("menu", 6);
        render();
    }

    public static void masterKlpd(){
        List<Klpd> klpds = Klpd.findAll();
        renderJSON(klpds);
    }
}
