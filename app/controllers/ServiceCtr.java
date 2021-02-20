package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jobs.ApplicationStartJob;
import models.Klpd;
import models.Penyedia;
import org.apache.commons.io.IOUtils;
import play.Logger;
import play.libs.XPath;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
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
