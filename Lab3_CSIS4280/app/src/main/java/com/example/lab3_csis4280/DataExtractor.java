package com.example.lab3_csis4280;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataExtractor {

    public void extract (Activity context, String data, String... columns) {

        Log.d(this.getClass().getName(),data);
        try {
            JSONArray ar = new JSONArray(data);

            JSONObject jobj;
            final TableLayout tbl = (TableLayout) context.findViewById(R.id.tableOutput);
            TableRow tr;
            TextView txt;
            String s;
            tbl.removeAllViews();

            // creating the header
            tr = new TableRow(context);

            TableLayout.LayoutParams lp =
                        new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);

            lp.setMargins(20,5,15,5); // left, top, right, bottom
            tr.setLayoutParams(lp);

            for (int y=0; y < columns.length; y++) {
                txt = new TextView(context);
                txt.setPadding(0,0,15,0);
                txt.setText(columns[y]);
                tr.addView(txt);
            }
            tbl.addView(tr);

            for (int x=0; x < ar.length(); x++) {
                tr = new TableRow(context);
                tr.setLayoutParams(lp);
                jobj = ar.getJSONObject(x);

                // getting the columns
                for (int y=0; y < columns.length; y++) {
                    txt = new TextView(context);
                    txt.setPadding(0,0,15,0);
                    s =jobj.getString(columns[y]);

                    txt.setText(s);
                    tr.addView(txt);
                }
                tbl.addView(tr);

            }
        } catch (Exception e) {
            Log.d(this.getClass().getName(), e.getMessage());
        }
    }
}
