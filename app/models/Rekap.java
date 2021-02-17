package models;

import com.google.gson.annotations.Expose;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jdbc.BaseTable;
import play.db.jdbc.Id;
import play.db.jdbc.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="paket_rekap")
public class Rekap extends BaseTable implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @Expose
    public Integer tahun_anggaran;

    @Id
    @Expose
    public String kode_kldi;

    @Expose
    public String nama_kldi;

    @Expose
    public Long total_anggaran;

    @Expose
    public Long jum_paket_tender;

    @Expose
    public Long jum_paket_nontender;

}
