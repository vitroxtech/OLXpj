package squaring.vitrox.olxpj.db;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import squaring.vitrox.olxpj.Model.Category;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "olxManager";
    private static final String TABLE_TRACKING = "olxTracking";
    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY_NAME = "name";
    private static final String KEY_CLICKS_No = "clicksNo";

    private static DatabaseHandler instance;

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* One instance for all the app then is always the same for all */
    public static synchronized DatabaseHandler getdatabaseHandler(Context context) {
        if (instance == null)
            instance = new DatabaseHandler(context);

        return instance;
    }

    // Creating Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_TRACKING + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY_NAME + " TEXT,"
                + KEY_CLICKS_No + " TEXT" + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKING);
        // Create tables again
        onCreate(db);
    }

    // Adding new CATEGORY
    public void addCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, category);
        values.put(KEY_CLICKS_No, 0);
        // Inserting Row
        db.insert(TABLE_TRACKING, null, values);
        db.close();
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRACKING, new String[]{KEY_ID,
                        KEY_CATEGORY_NAME, KEY_CLICKS_No}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        return category;
    }

    public Category getMostViewedCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRACKING, new String[]{KEY_ID, KEY_CATEGORY_NAME, "MAX(clicksNo)"}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        return category;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRACKING + " ORDER BY " + KEY_CLICKS_No + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category contact = new Category();
                contact.setId(cursor.getString(0));
                contact.setCategoryname(cursor.getString(1));
                contact.setClicks(cursor.getString(2));
                categoryList.add(contact);
            } while (cursor.moveToNext());
        }

        return categoryList;
    }

    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        int value = Integer.valueOf(category.getClicks());
        value++;
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, category.getCategoryname());
        values.put(KEY_CLICKS_No, String.valueOf(value));
        // updating row
        System.out.println(category.getCategoryname() + " " + String.valueOf(value));

        return db.update(TABLE_TRACKING, values, KEY_ID + " = ?",
                new String[]{category.getId()});


    }

}