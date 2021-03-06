package com.mmproduction.abcd.carreminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

//http://examples.javacodegeeks.com/android/core/database/android-database-example/
public class JCGSQLiteHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 3;
    // database name
    public static final String DATABASE_NAME = "MyCarsDB";
    public static final String CARS_TABLE_NAME = "cars";
    public static final String CARS_COLUMN_ID = "id";
    public static final String CARS_COLUMN_LICENCE = "licence";
    public static final String CARS_COLUMN_BRAND = "brand";
    public static final String CARS_COLUMN_USAGE = "usage";
    public static final String CARS_COLUMN_INSURANCE = "insurance";
    public static final String CARS_COLUMN_INSPECTION = "inspection";
    public static final String CARS_COLUMN_TAX = "tax";
    public static final String CARS_COLUMN_FIRE = "fire";
    public static final String CARS_COLUMN_MEDICAL = "medical";
    public static final String CARS_COLUMN_RATE = "rate";
    public static final String CARS_COLUMN_OIL_CHANGE = "oil";
    public static final String CARS_COLUMN_PARKING = "parking";
    public static final String CARS_COLUMN_ENGINE_AIR_FILTER = "eairfilter";
    public static final String CARS_COLUMN_CABIN_AIR_FILTER = "cairfilter";
    public static final String CARS_COLUMN_BATTERY = "battery";
    public static final String CARS_COLUMN_ANTIFREEZE = "antifreeze";
    public static final String CARS_COLUMN_TIRE_ROTATION = "tire";
    public static final String CARS_COLUMN_WINDSHIELD_WIPER = "wiper";

    private static final String[] COLUMNS = {CARS_COLUMN_ID, CARS_COLUMN_LICENCE, CARS_COLUMN_BRAND,
            CARS_COLUMN_USAGE, CARS_COLUMN_INSURANCE, CARS_COLUMN_INSPECTION, CARS_COLUMN_TAX,
            CARS_COLUMN_FIRE, CARS_COLUMN_MEDICAL, CARS_COLUMN_RATE, CARS_COLUMN_OIL_CHANGE, CARS_COLUMN_PARKING,
            CARS_COLUMN_ENGINE_AIR_FILTER, CARS_COLUMN_CABIN_AIR_FILTER, CARS_COLUMN_BATTERY,
            CARS_COLUMN_ANTIFREEZE, CARS_COLUMN_TIRE_ROTATION, CARS_COLUMN_WINDSHIELD_WIPER};

    public JCGSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create cars table
        String CREATE_CARS_TABLE = "CREATE TABLE cars ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "licence text, brand text, usage text, insurance text, inspection text, tax text, " +
                "fire text, medical text, rate text, oil text, parking text, eairfilter text, cairfilter text, " +
                "battery text, antifreeze text, tire text, wiper text)";
        db.execSQL(CREATE_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop cars table if already exists
        db.execSQL("DROP TABLE IF EXISTS cars");
        this.onCreate(db);
    }

    public void createCar(Car car) {
        // get reference of the CarDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();

        values.put("licence", car.getLicence());
        values.put("brand", car.getBrand());
        values.put("usage", car.getUsage());
        values.put("insurance", car.getInsurance());
        values.put("inspection", car.getInspection());
        values.put("tax", car.getTax());
        values.put("fire", car.getFire());
        values.put("medical", car.getMedical());
        values.put("rate", car.getRate());
        values.put("oil", car.getOil());
        values.put("parking", car.getParking());
        values.put("eairfilter", car.getEairfilter());
        values.put("cairfilter", car.getCairfilter());
        values.put("battery", car.getBattery());
        values.put("antifreeze", car.getAntifreeze());
        values.put("tire", car.getTire());
        values.put("wiper", car.getWiper());

        // insert book
        db.insert(CARS_TABLE_NAME, null, values);

        Log.d("debugv2", "in JSGSQO createCar");
        Log.d("debugv2", values.toString());

        // close database transaction
        db.close();
    }

    public Car findCar(int id) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get book query
        Cursor cursor = db.query(CARS_TABLE_NAME, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        Car car = new Car();
        car.setID(Integer.parseInt(cursor.getString(0)));
        car.setLicence(cursor.getString(1));
        car.setBrand(cursor.getString(2));
        car.setUsage(cursor.getString(3));
        car.setInsurance(cursor.getString(4));
        car.setInspection(cursor.getString(5));
        car.setTax(cursor.getString(6));
        car.setFire(cursor.getString(7));
        car.setMedical(cursor.getString(8));
        car.setRate(cursor.getString(9));
        car.setOil(cursor.getString(10));
        car.setParking(cursor.getString(11));
        car.setEairfilter(cursor.getString(12));
        car.setCairfilter(cursor.getString(13));
        car.setBattery(cursor.getString(14));
        car.setAntifreeze(cursor.getString(15));
        car.setTire(cursor.getString(16));
        car.setWiper(cursor.getString(17));

        return car;
    }

    public List getAllCars() {
        Log.d("debugv2", "in JSGSQO getAllCars");
        List cars = new LinkedList();

        // select book query
        String query = "SELECT  * FROM " + CARS_TABLE_NAME;

        // get reference of the CarDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        Car car = null;
        if (cursor.moveToFirst()) {
            do {
                car = new Car();
                car.setID(Integer.parseInt(cursor.getString(0)));
                car.setLicence(cursor.getString(1));
                car.setBrand(cursor.getString(2));
                car.setUsage(cursor.getString(3));
                car.setInsurance(cursor.getString(4));
                car.setInspection(cursor.getString(5));
                car.setTax(cursor.getString(6));
                car.setFire(cursor.getString(7));
                car.setMedical(cursor.getString(8));
                car.setRate(cursor.getString(9));
                car.setOil(cursor.getString(10));
                car.setParking(cursor.getString(11));
                car.setEairfilter(cursor.getString(12));
                car.setCairfilter(cursor.getString(13));
                car.setBattery(cursor.getString(14));
                car.setAntifreeze(cursor.getString(15));
                car.setTire(cursor.getString(16));
                car.setWiper(cursor.getString(17));

                // Add book to books
                cars.add(car);
            } while (cursor.moveToNext());
        }
        return cars;
    }

    public void updateCar(Car car) {

        // get reference of the CarDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put("licence", car.getLicence());
        values.put("brand", car.getBrand());
        values.put("usage", car.getUsage());
        values.put("insurance", car.getInsurance());
        values.put("inspection", car.getInspection());
        values.put("tax", car.getTax());
        values.put("fire", car.getFire());
        values.put("medical", car.getMedical());
        values.put("rate", car.getRate());
        values.put("oil", car.getOil());
        values.put("parking", car.getParking());
        values.put("eairfilter", car.getEairfilter());
        values.put("cairfilter", car.getCairfilter());
        values.put("battery", car.getBattery());
        values.put("antifreeze", car.getAntifreeze());
        values.put("tire", car.getTire());
        values.put("wiper", car.getWiper());


        db.update(CARS_TABLE_NAME, values, CARS_COLUMN_ID + " = " + String.valueOf(car.getID()), null);
        Log.d("DEBUG", "the car id is " + String.valueOf(car.getID()));
        Log.d("DEBUG", "CARS_TABLE_NAME" + CARS_TABLE_NAME);
        Log.d("DEBUG", "CARS_COLUMN_ID" + CARS_COLUMN_ID);
        Log.d("DEBUG", "values" + values.toString());

        db.close();
        //return i;
    }

    // Deleting single car
    public void deleteCar(Car car) {

        // get reference of the CarDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete book
        db.delete(CARS_TABLE_NAME, CARS_COLUMN_ID + " = ?", new String[] { String.valueOf(car.getID()) });
        db.close();
    }
}
