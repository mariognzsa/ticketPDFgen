package com.example.Parcial2.ExamenParcial2E1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    int desertNumber;
    public TemplatePDF templatePDF;
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create an ArrayList of postre objects
        final ArrayList<postre> desserts = new ArrayList<postre>();

        desserts.add(new postre("Dona",14, 0, R.drawable.dona));
        desserts.add(new postre("Galleta", 8, 0, R.drawable.galleta));
        desserts.add(new postre("Pedazo de pastel", 35, 0, R.drawable.pastel));
        desserts.add(new postre("Pay de calabaza", 28, 0, R.drawable.pay));

        // Create an {@link adaptador_postre}, whose data source is a list of
        // {@link postre}s. The adapter knows how to create list item views for each item
        // in the list.
        adaptador_postre flavorAdapter = new adaptador_postre(this, desserts);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.listview_dessert);
        listView.setAdapter(flavorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //postre dessert = desserts.get(i);
                switch(i) {
                    case 0:
                        Intent donut = new Intent(MainActivity.this, dona.class);
                        startActivity(donut);
                        break;
                    case 1:
                        Intent cookie = new Intent(MainActivity.this, galleta.class);
                        startActivity(cookie);
                        break;
                    case 2:
                        Intent pieceOfCake = new Intent(MainActivity.this, pedazo_pastel.class);
                        startActivity(pieceOfCake);
                        break;
                    case 3:
                        Intent pastry = new Intent(MainActivity.this, pay.class);
                        startActivity(pastry);
                        break;
                }
            }
        });
    }
    public void Decrement(View view) {

        LinearLayout parentRow = (LinearLayout) view.getParent();

        TextView quantityView = (TextView) parentRow.findViewById(R.id.dessert_number);
        String quantityString = quantityView.getText().toString();
        desertNumber = Integer.parseInt(quantityString);
        desertNumber -= 1;

        if (desertNumber < 0) {
            desertNumber = 0;
            Toast.makeText(MainActivity.this, "No se permite valor menor a 0",
                    Toast.LENGTH_SHORT).show();}
        quantityView.setText(String.valueOf(desertNumber));
    }
    public void Increment(View view) {

        LinearLayout parentRow = (LinearLayout) view.getParent();

        TextView quantityView = (TextView) parentRow.findViewById(R.id.dessert_number);
        String quantityString = quantityView.getText().toString();
        desertNumber = Integer.parseInt(quantityString);
        desertNumber += 1;

        if (desertNumber > 100) {
            desertNumber = 100;
            Toast.makeText(MainActivity.this, "No se permite valor mayor a 100",
                    Toast.LENGTH_SHORT).show();}
        quantityView.setText(String.valueOf(desertNumber));
    }
    public void pdfView(View view){

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String fecha = formatter.format(date);
            String[]header = {"Producto","Cantidad","Importe","Total Parcial"};
            ArrayList<String[]>rows = new ArrayList<>();
            ListView parent = (ListView) view.findViewById(R.id.listview_dessert);
            LinearLayout layout;
            int quantities;
            int monto;
            int monto_tot;
            int total_neto = 0;
            String producto;
            int limit = 4;
//        for(int i=0; i<limit; i++){
//            layout = (LinearLayout) parent.getChildAt(i);
//            TextView cantidad = layout.findViewById(R.id.dessert_number);
//            TextView productName = layout.findViewById(R.id.dessert_name);
//            TextView amount = layout.findViewById(R.id.dessert_amount);
//            quantities = Integer.parseInt(cantidad.getText().toString());
//            monto = Integer.parseInt(amount.getText().toString());
//            producto = productName.getText().toString();
//            monto_tot = quantities * monto;
//            total_neto += monto_tot;
//            rows.add(new String[]{producto, String.valueOf(quantities), String.valueOf(monto), String.valueOf(monto_tot)});
////            rows.add(new String[]{"Dona", "4", "23", "120"});
//        }
            rows.add(new String[]{" "," ","Total Neto:",String.valueOf(total_neto)});

            templatePDF = new TemplatePDF(getApplicationContext());
            templatePDF.openDocument();
            templatePDF.addMetaData("Ticket","Recibo", "LocalDessertStore");
            templatePDF.addHeader("Ticket de venta.", "Comprobante de pago.", fecha);
            templatePDF.createTable(header, rows);
            templatePDF.closeDocument();
            templatePDF.viewPDF();
        }
        else{
            requestStoragePermission();
        }

    }
    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("For us to continue with the creation of the ticket, we need you to grant us writing access")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String [] permissions, @NonNull int [] grantResults){
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}