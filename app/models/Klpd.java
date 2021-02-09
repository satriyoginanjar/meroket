package models;

import com.google.gson.annotations.Expose;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jdbc.BaseTable;
import play.db.jdbc.Id;

import java.io.Serializable;

public class Klpd extends BaseTable implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @Expose
    public String id;

    @Expose
    @Required
    public String jenis;

    @Expose
    public int kabupaten_id;

    @Expose
    @Required
    public String nama;

    @Expose
    public int prop_id;

    @Expose
    public String alamat;

    @Expose
    @MaxSize(100)
    @URL
    public String website;

}
