package com.mmproduction.abcd.carreminders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ViewCarsActivity extends BaseActivity {
    JCGSQLiteHelper db = new JCGSQLiteHelper(this);
    List<Car> list;

    private ListView m_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cars);
        super.onCreateDrawer(savedInstanceState);

        // get all the cars
        list = db.getAllCars();
        List listCars = new ArrayList();

        Log.d("DebugAlarm", "The list is: " + list.toString());


        for (int i = 0; i < list.size(); i++) {
            listCars.add(i, list.get(i));
        }

       ListView m_listview = (ListView) findViewById(R.id.list);

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.simple_list_item_3, android.R.id.text1, listCars){
                    //overriding the method so we can use both text items
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                        text1.setText(list.get(position).getLicence());
                        text2.setText(list.get(position).getBrand());
                        return view;
                    }
                };

        m_listview.setAdapter(adapter);

        m_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("DEBUG", "adapter: " + String.valueOf(adapter.getItem(position)));
                int idOfCar = Integer.parseInt(String.valueOf(adapter.getItem(position)));

                Car car = new Car();
                car.setID(idOfCar);

                Intent intent = new Intent(getApplicationContext(), ViewSingleCarActivity.class);

                intent.putExtra("id", idOfCar);
                startActivity(intent);
                finish();
            }
        });


    }





    }



