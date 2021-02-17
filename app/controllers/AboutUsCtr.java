package controllers;

public class AboutUsCtr extends Application{

    public static void index (){
        session.put("menu", 5);
        render();
    }
}
