package controllers;

import models.Klpd;
import models.Rekap;
import play.db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;

public class MonevCtr extends Application{

    public static void index(){
        session.put("menu", 1);
        render();
    }

    public static void pemula(){
        session.put("menu", 2);
        render();
    }

    public static void daftarKlpd(){
        session.put("menu", 3);
        render();
    }

    public static void detailAnggaranKlpd (String kdKlpd){
        session.put("menu", 4);
        if (kdKlpd != null){
            Klpd klpd = Klpd.findById(kdKlpd);
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            Integer jumlahSatker = 0;
            Integer jumPengadaanLangsung = 0;
            Integer jumPenunjukanLangsung = 0;
            Integer jumSayembara = 0;
            Integer jumTender = 0;
            Integer jumTenderCepat = 0;
            Integer jumSeleksi = 0;
            Integer jumEpurchasing = 0;
            Integer jumPaketSelesai = 0;
            Integer jumPaketBlmSelesai = 0;
            try{
                conn = DB.getConnection();
                st = conn.createStatement();
                rs = st.executeQuery("select count(id) from satuan_kerja where id_kldi = '"+kdKlpd+"' and tahun_aktif ilike '%2021%'");
                while (rs.next()){
                    jumlahSatker = rs.getInt(1);
                }
                rs = st.executeQuery("select count(id) from paket_penyedia_denormalisasi_2021 where kode_kldi = '"+kdKlpd+"' and metode_pengadaan = 8 and status = 3");
                while (rs.next()){
                    jumPengadaanLangsung = rs.getInt(1);
                }
                rs = st.executeQuery("select count(id) from paket_penyedia_denormalisasi_2021 where kode_kldi = '"+kdKlpd+"' and metode_pengadaan = 7 and status = 3");
                while (rs.next()){
                    jumPengadaanLangsung = rs.getInt(1);
                }
                rs = st.executeQuery("select count(id) from paket_penyedia_denormalisasi_2021 where kode_kldi = '"+kdKlpd+"' and metode_pengadaan = 10 and status = 3");
                while (rs.next()){
                    jumSayembara = rs.getInt(1);
                }
                rs = st.executeQuery("select count(id) from paket_penyedia_denormalisasi_2021 where kode_kldi = '"+kdKlpd+"' and metode_pengadaan = 13 and status = 3");
                while (rs.next()){
                    jumTender = rs.getInt(1);
                }
                rs = st.executeQuery("select count(id) from paket_penyedia_denormalisasi_2021 where kode_kldi = '"+kdKlpd+"' and metode_pengadaan = 14 and status = 3");
                while (rs.next()){
                    jumTenderCepat = rs.getInt(1);
                }
                rs = st.executeQuery("select count(id) from paket_penyedia_denormalisasi_2021 where kode_kldi = '"+kdKlpd+"' and metode_pengadaan = 15 and status = 3");
                while (rs.next()){
                    jumSeleksi = rs.getInt(1);
                }
                rs = st.executeQuery("select count(id) from paket_penyedia_denormalisasi_2021 where kode_kldi = '"+kdKlpd+"' and metode_pengadaan = 9 and status = 3");
                while (rs.next()){
                    jumEpurchasing = rs.getInt(1);
                }
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    rs.close();
                } catch (Exception e) { /* ignored */ }
                try {
                    st.close();
                } catch (Exception e) { /* ignored */ }
            }
            render(klpd,jumlahSatker,jumPengadaanLangsung,jumPenunjukanLangsung,jumSayembara,jumTender,jumTenderCepat,jumSeleksi,jumEpurchasing,jumPaketSelesai,jumPaketBlmSelesai);
        }else{
            index();
        }
    }

    public static void detailPaketNonTender(Long idPaket) throws ParseException {
        System.out.println("id nyaaaa "+idPaket);
        render();
    }


}
