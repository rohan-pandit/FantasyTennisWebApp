package dbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rohanpandit
 */
public class ReportWriter {

    private String table = "";
    public String errorMsg = "";

    public ReportWriter(DbConn dbc, String sql, String cssStyleSheet) {
        errorMsg = dbc.getErr();
        if (errorMsg.length() == 0) { // db connection is good

            try { // to create and execute sql

                // select all columns from tableName (but no rows) because
                // we are not interested in the data, just the metadata.
                PreparedStatement st = dbc.getConn().prepareStatement(sql);
                ResultSet results = st.executeQuery();
                ResultSetMetaData rsMetaData = results.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount();

                // create an HTML table that shows the most important metadata attributes
                // of each column from the specified table.
                /*table += ("<table  class=" + cssStyleSheet + "><tr><th style='text-align:left'>column name</th><th style='text-align:left'>type</th>"
                        + "<th style='text-align:right'>display size</th><th style='text-align:right'>precision</th><th style='text-align:right'>scale</th><th style='text-align:left'>autoincrement</th></tr>");
                 */
                table += "<table class='" + cssStyleSheet + "'>\n";
                table += "<tr>";

                String colType;
                String colNm;
                int i;
                for (i = 1; i <= numberOfColumns; ++i) {
                    colType = rsMetaData.getColumnTypeName(i);
                    colNm = rsMetaData.getColumnLabel(i);
                    System.out.println(colNm);
                    colNm = colNm.replaceAll("_", " ");
                    table += "<th style='text-align:";
                    if (colType.equals("INT") || colType.equals("DECIMAL") || colType.equals("DATE")) {
                        table += "right;";
                    } else if (colType.equals("VARCHAR") || colType.equals("LONGTEXT")) {
                        table += "left;";
                    } else {
                        table += "center;";
                    }
                    table += "'>" + colNm + "</th>";
                }
                table += "<tr>";
                while (results.next()) {
                    table += "<tr>";

                    for (i = 1; i <= numberOfColumns; ++i) {
                        colType = rsMetaData.getColumnTypeName(i);
                        colNm = rsMetaData.getColumnLabel(i);

                        if (colType.equals("INT")) {
                            table += FormatUtils.formatIntegerTd(results.getObject(colNm));
                        } else if (colType.equals("DECIMAL")) {
                            table += FormatUtils.formatDollarTd(results.getObject(colNm));
                        } else if (colType.equals("DATE")) {
                            table += FormatUtils.formatDateTd(results.getObject(colNm));
                        } else if (colType.equals("VARCHAR")) {
                            table += FormatUtils.formatStringTd(results.getObject(colNm));
                        } else if (colType.equals("LONGTEXT")) {
                            table += FormatUtils.formatStringTd(results.getObject(colNm));
                        }
                    }

                    table += "</tr>\n";
                }

                results.close();
                st.close();

                table += ("</table>");
            } /*for (i = 1; i <= numberOfColumns; i++) {
                    table += ("<tr><td style='text-align:left'>" + rsMetaData.getColumnName(i).replaceAll("_", " ") + "</td>");
                    table += ("<td style='text-align:left'>" + rsMetaData.getColumnTypeName(i) + "</td>");
                    table += ("<td style='text-align:right'>" + rsMetaData.getColumnDisplaySize(i) + "</td>");
                    table += ("<td style='text-align:right'>" + rsMetaData.getPrecision(i) + "</td>");
                    table += ("<td style='text-align:right'>" + rsMetaData.getScale(i) + "</td>");
                    table += ("<td style='text-align:left'>" + rsMetaData.isAutoIncrement(i) + "</td></tr>");
                }*/ // try to create and execute sql
            catch (Exception e) {
                errorMsg += ("There was a problem with your 'Select' statement :" + e.getMessage() + "<br/>");

            }
        }
    }

    public String error() {
        return errorMsg;
    }

    public String viewMetaData() {
        return table;
    }
}
