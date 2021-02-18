package controllers;

import com.google.gson.Gson;
import services.GenosUtil;

import java.util.Map;

public class DataTableCtr extends Application{

    public static void dataTableRupKldi2017(int tahun) {
        String jsonOutput = null;
        if (jsonOutput == null) {
            String[] aColumns = new String[]{
                    "paket_rekap.kode_kldi as id_kldi",
                    "paket_rekap.jenis as jenis",
                    "paket_rekap.nama_kldi as nama",
                    "paket_rekap.jum_paket_tender as tender",
                    "paket_rekap.jum_paket_nontender as nontender",
                    "paket_rekap.total_anggaran as anggaran",
                    "paket_rekap.total_anggaran as anggaran2"
            };
            String whereClause = "paket_rekap.tahun_anggaran = " + tahun + " and paket_rekap.jenis in ('KEMENTERIAN','LEMBAGA')";
            Map<String, Object> output = GenosUtil.getJSONDataTableWithoutDistinct(params, "paket_rekap", null, whereClause,
                    aColumns, null, null, null, "paket_rekap.total_anggaran desc");
            Gson gson = new Gson();
            jsonOutput = gson.toJson(output);
        }
        renderJSON(jsonOutput);
    }

    public static void dataTableNonTenderKldi(String kdKlpd, int tahun) {
        String jsonOutput = null;
        if (jsonOutput == null) {
            String[] aColumns = new String[]{
                    "nt.id as id",
                    "nt.nama_paket as nama_paket",
                    "nt.mp_string as mp",
                    "nt.jp_string as jp",
                    "nt.lokasi as lokasi",
                    "nt.pagu as pagu",
            };
            String joinSql = "(SELECT \n" +
                    " paket_penyedia_denormalisasi_2021.id as id\n" +
                    " ,paket_penyedia_denormalisasi_2021.nama as nama_paket\n" +
                    " ,paket_penyedia_denormalisasi_2021.metode_pengadaan_string as mp_string\n" +
                    " ,paket_penyedia_denormalisasi_2021.jenis_pengadaan_string as jp_string\n" +
                    " ,paket_penyedia_denormalisasi_2021.lokasi as lokasi\n" +
                    " ,paket_penyedia_denormalisasi_2021.pagu as pagu\n" +
                    " FROM paket_penyedia_denormalisasi_2021 \n" +
                    " where kode_kldi = '"+kdKlpd+"' and metode_pengadaan in (7,8,10,11)\n" +
                    " limit 20 ) as nt";

            Map<String, Object> output = GenosUtil.getJSONDataTableWithoutDistinct(params, joinSql, null, null,
                    aColumns, null, null, null, null);
            Gson gson = new Gson();
            jsonOutput = gson.toJson(output);
        }
        renderJSON(jsonOutput);
    }

    public static void dataTableTenderKldi(String kdKlpd, int tahun) {
        String jsonOutput = null;
        if (jsonOutput == null) {
            String[] aColumns = new String[]{
                    "nt.id as id",
                    "nt.nama_paket as nama_paket",
                    "nt.mp_string as mp",
                    "nt.jp_string as jp",
                    "nt.lokasi as lokasi",
                    "nt.pagu as pagu",
            };
            String joinSql = "(SELECT \n" +
                    " paket_penyedia_denormalisasi_2021.id as id\n" +
                    " ,paket_penyedia_denormalisasi_2021.nama as nama_paket\n" +
                    " ,paket_penyedia_denormalisasi_2021.metode_pengadaan_string as mp_string\n" +
                    " ,paket_penyedia_denormalisasi_2021.jenis_pengadaan_string as jp_string\n" +
                    " ,paket_penyedia_denormalisasi_2021.lokasi as lokasi\n" +
                    " ,paket_penyedia_denormalisasi_2021.pagu as pagu\n" +
                    " FROM paket_penyedia_denormalisasi_2021 \n" +
                    " where kode_kldi = '"+kdKlpd+"' and metode_pengadaan in (13,14,15)\n" +
                    " limit 20 ) as nt";

            Map<String, Object> output = GenosUtil.getJSONDataTableWithoutDistinct(params, joinSql, null, null,
                    aColumns, null, null, null, null);
            Gson gson = new Gson();
            jsonOutput = gson.toJson(output);
        }
        renderJSON(jsonOutput);
    }

}
