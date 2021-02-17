package models;

import com.google.gson.annotations.Expose;
import play.db.jdbc.BaseTable;
import play.db.jdbc.Id;
import play.db.jdbc.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name="paket_penyedia_denormalisasi_2021")
public class Penyedia extends BaseTable implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @Expose
    public Long id;

    @Expose
    public String kegiatan;

    @Expose
    public String nama;

    @Expose
    public Integer jenis_pengadaan;

    @Expose
    public Integer metode_pengadaan;

    @Expose
    public String lokasi;

    @Expose
    public String keterangan;

    @Expose
    public Long pagu;

    @Expose
    public String volume;

    @Expose
    public Integer tanggal_akhir_pekerjaan;

    @Expose
    public Integer tanggal_akhir_pengadaan;

    @Expose
    public Integer tanggal_awal_pekerjaan;

    @Expose
    public Integer tanggal_awal_pengadaan;

    @Expose
    public Date auditupdate;

    @Expose
    public String kode_kldi;

    @Expose
    public String nama_kldi;

    @Expose
    public String nama_satker;

    @Expose
    public String sumber_dana_string;

    @Expose
    public String jenis_pengadaan_string;

    @Expose
    public String metode_pengadaan_string;

    @Expose
    public Date tanggal_pengumuman;

    @Expose
    public String bulan_awal_pengadaan;

    @Expose
    public String bulan_akhir_pengadaan;

    @Expose
    public String bulan_awal_pekerjaan;

    @Expose
    public String bulan_akhir_pekerjaan;

    @Expose
    public Boolean is_tkdn;

    @Expose
    public String spesifikasi;

    @Expose
    public Integer status;

    @Expose
    public Boolean umkm;












}
