package controllers;

import play.mvc.*;
import java.util.*;

public class Application extends Controller {

    public static int TAHUN_CALENDAR;
    public static int BULAN_CALENDAR;
    public static int HARI_CALENDAR;

    @Before
    static void interuptor(){

        TAHUN_CALENDAR = Calendar.getInstance().get(Calendar.YEAR);
        if(session.get("tahunAnggaranPilihan")==null) {
            session.put("tahunAnggaranPilihan", TAHUN_CALENDAR);
        }

        renderArgs.put("TAHUN_ANGGARAN_SESSION", getTahunAnggaranSession());
        renderArgs.put("TAHUN_CALENDAR", TAHUN_CALENDAR);
    }

    public static int getTahunAnggaranSession(){
        if(session.get("tahunAnggaranPilihan") == null) {
            session.put("tahunAnggaranPilihan", TAHUN_CALENDAR);
        }
        return Integer.parseInt(session.get("tahunAnggaranPilihan"));
    }

    public static void index() {
        session.put("menu", 99);
        render();
    }

}