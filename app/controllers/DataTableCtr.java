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

}
