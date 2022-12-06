package shope.three3pro.shope_All.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "fav";

    // Table columns
    public static final String _ID = "_id";
    public static final String product_name = "product_name";
    public static final String PHP_ID = "php_id";
    public static final String product_price = "product_price";
    public static final String product_size = "product_size";
    public static final String product_point = "product_point";
    public static final String product_description = "product_description";
    public static final String trader_name = "trader_name";
    public static final String product_image = "product_image";
    public static final String check_fav = "check_fav";

    // Database Information
    static final String DB_NAME = "Favorite.DB";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PHP_ID + " TEXT NOT NULL, "
            + product_name + " TEXT NOT NULL, "
            + product_size + " TEXT NOT NULL, "
            + product_point + " TEXT NOT NULL, "
            + product_description + " TEXT NOT NULL, "
            + trader_name + " TEXT NOT NULL, "
            + product_image + " TEXT NOT NULL, "
            + check_fav + " TEXT NOT NULL, "
            + product_price + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
