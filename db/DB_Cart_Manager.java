package shope.three3pro.shope_All.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import shope.three3pro.shope_All.model.productModel;


public class DB_Cart_Manager {
    private Database_Cart_Helper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DB_Cart_Manager(Context c) {
        context = c;
    }

    public DB_Cart_Manager open() throws SQLException {
        dbHelper = new Database_Cart_Helper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }



    public void insert(String PHP_id
            ,String product_name
            ,String product_price
            , String product_size
            ,String product_point
            ,String product_description
            , String trader_name
            ,String fav_check
            ,String product_image ) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(Database_Cart_Helper.PHP_ID, PHP_id);
        contentValue.put(Database_Cart_Helper.product_size, product_size);
        contentValue.put(Database_Cart_Helper.product_name, product_name);
        contentValue.put(Database_Cart_Helper.product_price, product_price);
        contentValue.put(Database_Cart_Helper.product_point, product_point);
        contentValue.put(Database_Cart_Helper.product_description, product_description);
        contentValue.put(Database_Cart_Helper.trader_name, trader_name);
        contentValue.put(Database_Cart_Helper.check_fav, fav_check);
        contentValue.put(Database_Cart_Helper.product_image, product_image);
        database.insert(Database_Cart_Helper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {
                Database_Cart_Helper._ID
                ,Database_Cart_Helper.PHP_ID
                ,Database_Cart_Helper.product_name
                ,Database_Cart_Helper.product_size
                ,Database_Cart_Helper.product_price
                ,Database_Cart_Helper.product_point
                ,Database_Cart_Helper.product_description
                ,Database_Cart_Helper.trader_name
                ,Database_Cart_Helper.product_image};
        Cursor cursor = database.query(Database_Cart_Helper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String PHP_id
            ,String product_name
            ,String product_price
            , String product_size
            ,String product_point
            ,String product_description
            , String trader_name
            , String fav_check
            ,String product_image ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database_Cart_Helper.PHP_ID, PHP_id);
        contentValues.put(Database_Cart_Helper.product_size, product_size);
        contentValues.put(Database_Cart_Helper.product_name,product_name);
        contentValues.put(Database_Cart_Helper.product_price, product_price);
        contentValues.put(Database_Cart_Helper.product_point, product_point);
        contentValues.put(Database_Cart_Helper.product_description, product_description);
        contentValues.put(Database_Cart_Helper.trader_name, trader_name);
        contentValues.put(Database_Cart_Helper.check_fav, fav_check);
        contentValues.put(Database_Cart_Helper.product_image, product_image);
        int i = database.update(Database_Cart_Helper.TABLE_NAME, contentValues, Database_Cart_Helper._ID + " = " + _id, null);
        return i;
    }

    public void delete(int _id) {
        database.delete(Database_Cart_Helper.TABLE_NAME, Database_Cart_Helper._ID + "=" + _id, null);
    }

    public ArrayList<productModel> readCourses() {
        // on below line we are creating a
        // database for reading our database.


        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = database.rawQuery("SELECT * FROM " + Database_Cart_Helper.TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<productModel> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new productModel(
                        cursorCourses.getInt(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getString(6),
                        cursorCourses.getString(7),
                        cursorCourses.getString(8),
                        cursorCourses.getString(9)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

}
