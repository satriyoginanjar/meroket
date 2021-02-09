package services;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import play.Logger;
import play.cache.Cache;
import play.db.DB;
import play.mvc.Scope;
import utils.FormatUtils;
import utils.SqlSafeEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class GenosUtil {
    public static Map<String, Object> getJSONDataTableWithoutDistinct(Scope.Params params, String table, String innerJoinStatement,
                                                                      String additionalWhereClause, String[] aColumns, String groupClause,
                                                                      int[] searchAble, String withClause, String orderColumnDefault) {
        return getJSONDataTableWithoutDistinct(params, table, innerJoinStatement, additionalWhereClause, aColumns, groupClause, searchAble, withClause, orderColumnDefault, "10mn");
    }

    public static Map<String, Object> getJSONDataTableWithoutDistinct(Scope.Params params, String table, String innerJoinStatement,
                                                                      String additionalWhereClause, String[] aColumns, String groupClause,
                                                                      int[] searchAble, String withClause, String orderColumnDefault, String duration) {

        StopWatch sw=new StopWatch();
        sw.start();

        if (innerJoinStatement == null) {
            innerJoinStatement = " ";
        }
        if (groupClause == null) {
            groupClause = " ";
        }

        // PAGING
        String sLimit = "";
        if (params.get("iDisplayStart") != null && !params.get("iDisplayLength").equalsIgnoreCase("-1")) {
            sLimit = "OFFSET " + params.get("iDisplayStart") + " LIMIT " + params.get("iDisplayLength");
        }

        // ORDERING
        String sOrder = "";
        if(orderColumnDefault!=null){
            sOrder = " order by "+orderColumnDefault;
        }
        if (params.get("iSortCol_0") != null && !params.get("iSortCol_0").equalsIgnoreCase("0") ) {
            sOrder = "ORDER BY  ";
            for (int i = 0; i < Integer.parseInt(params.get("iSortingCols")); i++) {
                int iSortCol_ = Integer.parseInt(params.get("iSortCol_" + i));
                String sSortDir_ = params.get("sSortDir_" + i);
                if (params.get("bSortable_" + iSortCol_).equalsIgnoreCase("true")) {
                    String column="";
                    if(aColumns[i].contains(" as ")){
                        column=aColumns[iSortCol_].split(" as ")[1];
                    }else{
                        column=aColumns[iSortCol_];
                    }
                    sOrder += "" + column + " " + sSortDir_ + ", ";
                }else if(params.get("iSortCol_0").equalsIgnoreCase("0")){
                    if (orderColumnDefault != null){
                        sOrder += "" + orderColumnDefault+" ";
                    }
                }
            }
            sOrder = sOrder.substring(0, sOrder.length() - 2);
            if (sOrder.equalsIgnoreCase("ORDER BY")) {
                sOrder = "";
            }
        }
        if(sOrder.contains(" as ")){
            sOrder=sOrder.split(" as ")[0];
        }

        // FILTERING
        String sWhere = "";
        if(searchAble == null){//Cari semua. Default
            if (SqlSafeEncoder.enc(params.get("sSearch")) != null && !SqlSafeEncoder.enc(params.get("sSearch")).isEmpty()) {
                sWhere = "WHERE (";
                for (int i = 0; i < aColumns.length; i++) {
                    String column;

                    if(aColumns[i].contains(" as ")){
                        column=aColumns[i].split(" as ")[0];
                    }else{
                        column=aColumns[i];
                    }
                    sWhere += "LOWER(" + column + "::varchar) LIKE LOWER('%" + SqlSafeEncoder.enc(params.get("sSearch")) + "%') OR ";
                }
                sWhere = sWhere.substring(0, sWhere.length() - 3);
                sWhere += ")";
            }
        }else{
            if (SqlSafeEncoder.enc(params.get("sSearch")) != null && !SqlSafeEncoder.enc(params.get("sSearch")).isEmpty()) {
                sWhere = "WHERE (";
                for (int i = 0; i < aColumns.length; i++) {
                    for(int j =0; j < searchAble.length; j++){
                        if(i==searchAble[j]){
                            String column;

                            if(aColumns[i].contains(" as ")){
                                column=aColumns[i].split(" as ")[0];
                            }else{
                                column=aColumns[i];
                            }
                            sWhere += "LOWER(" + column + "::varchar) LIKE LOWER('%" + SqlSafeEncoder.enc(params.get("sSearch")) + "%') OR ";
                        }
                    }
                }
                sWhere = sWhere.substring(0, sWhere.length() - 3);
                sWhere += ")";
            }
        }

        // INDIVIDUAL COLUMN FILTERING
        for (int i = 0; i < aColumns.length; i++) {

            String bSearchable_ = params.get("bSearchable_" + i);
            String sSearch_ = params.get("sSearch_" + i);

            if (bSearchable_ != null && bSearchable_.equalsIgnoreCase("true") && !sSearch_.isEmpty()) {
                if (sWhere.isEmpty()) {
                    sWhere = "WHERE ";
                } else {
                    sWhere += " AND ";
                }
                String column;

                if(aColumns[i].contains(" as ")){
                    column=aColumns[i].split(" as ")[0];
                }else{
                    column=aColumns[i];
                }
                if(sSearch_.contains("#"))
                    sWhere += "LOWER(" + column + "::varchar) LIKE LOWER('" + sSearch_.substring(0,sSearch_.length()-1) + "') ";
                else
                    sWhere += "LOWER(" + column + "::varchar) LIKE LOWER('%" + sSearch_ + "%') ";
            }
        }

        String _implode = StringUtils.join(aColumns, ", ").replace(" , ", " ");
        String awal = aColumns[0];

        if(aColumns[0].contains(" as ")){
            awal=aColumns[0].split(" as ")[0];
        }

        // COUNT ALL RESULT WITHOUT LIMITATION
        // From MySQL Query Function : SQL_CALC_FOUND_ROWS
        String sQuery;
        if (!sWhere.isEmpty()) {
            sWhere = sWhere + (additionalWhereClause != null ? " AND " + additionalWhereClause : "");
        } else {
            sWhere = (additionalWhereClause != null ? " WHERE " + additionalWhereClause : "");
        }

        if(groupClause!=null||!groupClause.equalsIgnoreCase("")){
            sQuery = "SELECT COUNT (distinct "+awal+") "
                    + "FROM " + table+""+
                    " "+innerJoinStatement+" "
                    + sWhere+" ";
        }else{
            sQuery = "SELECT COUNT ("+awal+") "
                    + "FROM " + table+""+
                    " "+innerJoinStatement+" "
                    + sWhere+" ";
        }

        if(withClause!=null) sQuery=withClause+"\n"+sQuery;

        String md5StringSqueryTotal = utils.CommonUtil.getMD5Hash("sirup_util_total_"+sQuery);

        Integer iFilteredTotal = (Integer) Cache.get(md5StringSqueryTotal);
        Boolean isNewPaket = (Boolean) Cache.get("paketBaru");
        if(iFilteredTotal == null || (isNewPaket != null && isNewPaket)) {

            Logger.info("rResultFilterTotal: " + sQuery);
            ResultSet rResultFilterTotal = DB.executeQuery(sQuery);
            //Integer iFilteredTotal = (int) (long) PaketSwakelola.count("tahun_anggaran = ? AND kode_kldi = ?", 2018, "K10");

            // Count result set

            try {
                while (rResultFilterTotal.next()) {
                    iFilteredTotal = rResultFilterTotal.getInt("count");
                }
                rResultFilterTotal.close();
            } catch (SQLException e) {
                Logger.info("@DataTable.getJSONDataTable -> Tidak dapat eksekusi Result Set rResultFilterTotal: ", e);
                Logger.error("@DataTable.getJSONDataTable -> Tidak dapat eksekusi Result Set rResultFilterTotal!");
            }

            Cache.set(md5StringSqueryTotal, iFilteredTotal, duration);
        }

        // FIND ALL RESULT WITH LIMITATION
        // From MySQL Query Function : SQL_FOUND_ROWS
        sQuery = "SELECT  " + _implode + " "
                + "FROM " + table + " "
                + innerJoinStatement + " " // PLUS INNER JOIN
                + sWhere + " "
                + groupClause+ " "
                + sOrder + " "
                + sLimit;
        if(withClause!=null) sQuery=withClause+"\n"+sQuery;


        Map<String, Object> output = new HashMap<String, Object>();
        Integer sEcho = 0;
        try {
            sEcho = Integer.parseInt(params.get("sEcho"));
        } catch (NumberFormatException e) {
            Logger.info("@DataTable.getJSONDataTable -> sEcho is NULL!: ", e);
            Logger.error("@DataTable.getJSONDataTable -> sEcho is NULL!");
        }
        output.put("sEcho", sEcho);
        output.put("iTotalDisplayRecords", iFilteredTotal);

        String md5StringSqueryData = utils.CommonUtil.getMD5Hash("sirup_util_data_"+sQuery);


        ArrayList<String[]> rData = (ArrayList<String[]>) Cache.get(md5StringSqueryData);
        if(rData == null || (isNewPaket != null && isNewPaket) ) {
            rData = new ArrayList<String[]>();
            Logger.info(sQuery);
            ResultSet rResult = DB.executeQuery(sQuery);

            String[] tmp;
            try {
                while (rResult.next()) {
                    tmp = new String[aColumns.length];
                    for (int i = 0; i < aColumns.length; i++) {
                        Object obj;
                        // Get column name displayed by PostgreSQL Query Result
                        String column;
                        if (aColumns[i].contains(" as ") && aColumns[i].contains("+")) {
                            //column = aColumns[i].split("\\+")[1].split("as ")[1];
                            if(aColumns[i].split("\\+")[1].contains("as ")==true){
                                column= aColumns[i].split("\\+")[1].split("as ")[1];

                            }else{
                                column = aColumns[i].split("as ")[1];
                            }
                        } else if (aColumns[i].contains(" as ")) {
                            column = aColumns[i].split("\\.")[1].split("as ")[1];
                        } else {
                            column = aColumns[i].split("\\.")[1];
                        }
                        if (!innerJoinStatement.isEmpty()) {
                            obj = rResult.getObject(column);
                        } else {
                            obj = rResult.getObject(column);
                        }
                        String aRow;
                        if (obj instanceof Date) {
                            aRow = FormatUtils.formatDateTimeInd((Date) obj);
                        } else if (obj instanceof Double && (table.equalsIgnoreCase("paket") || table.equalsIgnoreCase("lelang_seleksi"))) {
                            // TODO: Mungkin akan banyak menguras resource untuk pengecekan ini!
                            aRow = FormatUtils.formatCurrenciesJuta((Double) obj);
                        } else if (obj == null) {
                            aRow = "N/A";
                        } else {
                            aRow = String.valueOf(obj);
                        }

                        if (!aColumns[i].isEmpty() || aColumns[i].equalsIgnoreCase(" ")) {
                            tmp[i] = aRow;
                        } else {
                            tmp[i] = "N/A";
                        }
                    }
                    rData.add(tmp);
                }
                rResult.close();
            } catch (SQLException e) {
                Logger.error("@DataTable.getJSONDataTable -> Tidak dapat eksekusi Result Set rResult! " + e);
            }
            sw.stop();
            if (sw.getTime() > 1000) {
                Logger.warn("Duration SQL: %s, SQL: %s", sw, sQuery);
            }

            Cache.set(md5StringSqueryData, rData, duration);
            Cache.set("paketBaru", false, "10mn");
        }
        output.put("aaData", rData);
        return output;
    }
    private static Date convertDate(Date dateFrom){
        TimeZone tz = TimeZone.getDefault();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a");
        String dateString = formatter.format(dateFrom);
        System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
        System.out.println("TimeZone : " + tz);
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a");
        sdfAmerica.setTimeZone(tz);

        try {
            Date date = formatter.parse(dateString);
            String sDateInAmerica = sdfAmerica.format(date); // Convert to String first
            return formatter.parse(sDateInAmerica); // Create a new Date object
//            return null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFrom;
    }
}
