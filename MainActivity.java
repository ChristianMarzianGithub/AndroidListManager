package com.example.chris.newmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText buckysInput;
    TextView buckysText;
    MyDBHandler dbHandler;
    TextView AnzahlText;
    TableRow x;
    ListView listView;


    String[] values = new String[] { "Android List View",
            "Adapter implementation",
            "Simple List View In Android",
            "Create List View Android",
            "Android Example",
            "List View Source Code",
            "List View Array Adapter",
            "Android Example List View"
    };

    ArrayList<String> myArrayList=
            new ArrayList<String>();


    String y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buckysInput = (EditText) findViewById(R.id.buckysInput);
        //buckysText = (TextView) findViewById(R.id.buckysText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        //AnzahlText = (TextView) findViewById(R.id.textViewAnzahl);

        listView = (ListView) findViewById(R.id.listView);

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

}
