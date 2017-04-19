/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package ms_br.appriuso.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandlerObject extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandlerObject.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "android_api2";

	// Object table name
	private static final String TABLE_OBJECT = "object2";

	// Object Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_TITLE = "title";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_IMAGE_1 = "image1";
	private static final String KEY_IMAGE_2 = "image2";
	private static final String KEY_IMAGE_3 = "image3";
	private static final String KEY_IMAGE_4 = "image4";
	private static final String KEY_IMAGE_5 = "image5";
	private static final String KEY_IMAGE_6 = "image6";
	private static final String KEY_IMAGE_7 = "image7";
	private static final String KEY_IMAGE_8 = "image8";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";


	public SQLiteHandlerObject(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		// Object table
		String CREATE_OBJECT_TABLE = "CREATE TABLE " + TABLE_OBJECT + "("
				+ KEY_ID + " integer primary key autoincrement, "
				+ KEY_CATEGORY + " TEXT, "
				+ KEY_TITLE + " TEXT, "
				+ KEY_DESCRIPTION + " TEXT, "
				+ KEY_LATITUDE + " TEXT, "
				+ KEY_LONGITUDE + " TEXT, "
				+ KEY_NAME + " TEXT, "
				+ KEY_EMAIL + " TEXT, "
				+ KEY_IMAGE_1 + " TEXT, "
				+ KEY_IMAGE_2 + " TEXT, "
				+ KEY_IMAGE_3 + " TEXT, "
				+ KEY_IMAGE_4 + " TEXT, "
				+ KEY_IMAGE_5 + " TEXT, "
				+ KEY_IMAGE_6 + " TEXT, "
				+ KEY_IMAGE_7 + " TEXT, "
				+ KEY_IMAGE_8 + " TEXT, "
				+ KEY_UID + " TEXT, "
				+ KEY_CREATED_AT + " TEXT " + ")";
		db.execSQL(CREATE_OBJECT_TABLE);

		Log.d(TAG, "Database tables object created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECT);
		// Create tables again
		onCreate(db);

	}

	/**
	 * Storing object details in database
	 * */
	public void addObject(String category, String title, String description,
			String latitude, String longitude, String name, String email, String image1,
						  String image2, String image3, String image4, String image5, String image6,
						  	String image7, String image8, String uid, String created_at) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_CATEGORY, category);
		values.put(KEY_TITLE, title);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_LATITUDE, latitude);
		values.put(KEY_LONGITUDE, longitude);
		values.put(KEY_NAME, name);
		values.put(KEY_EMAIL, email);
		values.put(KEY_IMAGE_1, image1);
		values.put(KEY_IMAGE_2, image2);
		values.put(KEY_IMAGE_3, image3);
		values.put(KEY_IMAGE_4, image4);
		values.put(KEY_IMAGE_5, image5);
		values.put(KEY_IMAGE_6, image6);
		values.put(KEY_IMAGE_7, image7);
		values.put(KEY_IMAGE_8, image8);
		values.put(KEY_UID, uid);
		values.put(KEY_CREATED_AT, created_at);

		// Inserting Row
		long id = db.insert(TABLE_OBJECT, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	/*
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}*/

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	/*public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}
	*/

}
