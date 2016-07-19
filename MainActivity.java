package com.example.chris.newmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

public class MainActivity extends AppCompatActivity {

    EditText buckysInput;
    TextView buckysText;
    MyDBHandler dbHandler;
    TextView AnzahlText;
    TableRow x;
    ListView listView;
    TextView text1;
    TextView text2;




    ArrayList<String> myArrayList=
            new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buckysInput = (EditText) findViewById(R.id.buckysInput);
        dbHandler = new MyDBHandler(this, null, null, 1);
        listView = (ListView) findViewById(R.id.listView);
        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, myArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                //Log.v("long clicked","pos: " + pos);
                myArrayList.remove(pos);
                listView.invalidateViews();

                return true;
            }
        });


        printDatabase();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();




            String scanFormat = scanningResult.getFormatName();

            Products product = new Products(scanContent);
            dbHandler.addProduct(product);
            printDatabase();
            listView.invalidateViews();
            buckysInput.setText("");
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    public void addButtonClicked(View view){
            Products product = new Products(buckysInput.getText().toString());
            dbHandler.addProduct(product);

            printDatabase();
            listView.invalidateViews();
            buckysInput.setText("");

    }

    public void dAbutton(View v){
        dbHandler.deleteAll();
        printDatabase();
    }

    public void deleteButtonClicked(View view){
        String inputText = buckysInput.getText().toString();
        dbHandler.deleteProduct(inputText);
        buckysInput.setText("");
        //printDatabase();
    }

    public void printDatabase(){
        myArrayList = dbHandler.databaseToString(myArrayList);

        listView.invalidateViews();
    }


    public void anzahlButtonClicked(View views){
        int anzahl = dbHandler.getAmountOfValues();
        AnzahlText.setText(Integer.toString(anzahl));
    }

    public void scanIt(View v){
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();

    }

}
