package models;

import com.google.gson.annotations.Expose;
import play.db.jdbc.BaseTable;
import play.db.jdbc.Id;
import play.db.jdbc.Table;

import java.io.Serializable;

@Table(name="satuan_kerja")
public class Satker extends BaseTable implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    public Long id;

    @Expose
    public Boolean aktif;

    @Expose
    public String id_kldi;

    @Expose
    public String id_satker;

    @Expose
    public String nama;

    @Expose
    public Boolean is_deleted;

    @Expose
    public String tahun_aktif;

    @Expose
    public Boolean is_not_rekap_display;

    @Expose
    public Boolean is_integrasi_rup;


}
