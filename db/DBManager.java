package shope.three3pro.shope_All.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import shope.three3pro.shope_All.model.productModel;


public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
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
            , String check_fav
            ,String product_image ) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.PHP_ID, PHP_id);
        contentValue.put(DatabaseHelper.product_size, product_size);
        contentValue.put(DatabaseHelper.product_name, product_name);
        contentValue.put(DatabaseHelper.product_price, product_price);
        contentValue.put(DatabaseHelper.product_point, product_point);
        contentValue.put(DatabaseHelper.product_description, product_description);
        contentValue.put(DatabaseHelper.trader_name, trader_name);
        contentValue.put(DatabaseHelper.product_image, product_image);
        contentValue.put(DatabaseHelper.check_fav, check_fav);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {
                DatabaseHelper._ID
                ,DatabaseHelper.PHP_ID
                ,DatabaseHelper.product_name
                ,DatabaseHelper.product_size
                ,DatabaseHelper.product_price
                ,DatabaseHelper.product_point
                ,DatabaseHelper.product_description
                ,DatabaseHelper.trader_name
                ,DatabaseHelper.product_image};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(int  _id, String PHP_id
            ,String product_name
            ,String product_price
            , String product_size
            ,String product_point
            ,String product_description
            , String trader_name
            , String check_fav
            ,String product_image ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PHP_ID, PHP_id);
        contentValues.put(DatabaseHelper.product_size, product_size);
        contentValues.put(DatabaseHelper.product_name,product_name);
        contentValues.put(DatabaseHelper.product_price, product_price);
        contentValues.put(DatabaseHelper.product_point, product_point);
        contentValues.put(DatabaseHelper.product_description, product_description);
        contentValues.put(DatabaseHelper.trader_name, trader_name);
        contentValues.put(DatabaseHelper.check_fav, check_fav);
        contentValues.put(DatabaseHelper.product_image, product_image);

        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(String _id) {
        int id_php=Integer.parseInt(_id);
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + id_php, null);
    }

    public ArrayList<productModel> readCourses() {
        // on below line we are creating a
        // database for reading our database.


        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<productModel> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new productModel(cursorCourses.getInt(1),
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
