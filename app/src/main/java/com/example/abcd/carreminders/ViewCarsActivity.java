package com.example.abcd.carreminders;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class ViewCarsActivity extends BaseActivity {
    JCGSQLiteHelper db = new JCGSQLiteHelper(this);
    List<Car> list;
    ArrayAdapter myAdapter;

    private ListView m_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cars);
        super.onCreateDrawer(savedInstanceState);

        // drop this database if already exists
        db.onUpgrade(db.getWritableDatabase(), 1, 2);
        Car car = new Car();
        car.setLicence("Car1");
        car.setBrand("Brand1");
        db.createCar(car);

        Car car2 = new Car();
        car.setLicence("Car2");
        car.setBrand("Brand2");
        db.createCar(car);
/*
        // get all the cars
        list = db.getAllCars();
        List listTitle = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            listTitle.add(i, list.get(i).getLicence());
        }

        myAdapter = new ArrayAdapter(this, R.layout.row_layout, R.id.listText, listTitle);*/

        ListView m_listview = (ListView) findViewById(R.id.list);

        String[] items = new String[] {"Item 1", "Item 2", "Item 3"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        m_listview.setAdapter(adapter);


        //getListView().setOnItemClickListener(this);
        //setListAdapter(myAdapter);
    }

    }



