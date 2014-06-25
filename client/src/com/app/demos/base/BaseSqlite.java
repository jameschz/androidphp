package com.app.demos.base;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseSqlite {

	private static final String DB_NAME = "demos.db";
	private static final int DB_VERSION = 1;
	
	private DbHelper dbh = null;
	private SQLiteDatabase db = null;
	private Cursor cursor = null;
	
	public BaseSqlite(Context context) {
		dbh = new DbHelper(context, DB_NAME, null, DB_VERSION);
	}

	public void create (ContentValues values) {
		try {
			db = dbh.getWritableDatabase();
			db.insert(tableName(), null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void update (ContentValues values, String where, String[] params) {
		try {
			db = dbh.getWritableDatabase();
			db.update(tableName(), values, where, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void delete (String where, String[] params) {
		try {
			db = dbh.getWritableDatabase();
			db.delete(tableName(), where, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public ArrayList<ArrayList<String>> query (String where, String[] params) {
		ArrayList<ArrayList<String>> rList = new ArrayList<ArrayList<String>>();
		try {
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), where, params, null, null, null);
			while (cursor.moveToNext()) {
				int i = cursor.getColumnCount();
				ArrayList<String> rRow = new ArrayList<String>();
				while (i >= 0) {
					rRow.add(i, cursor.getString(i));
				}
				rList.add(rRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
		return rList;
	}
	
	public int count (String where, String[] params) {
		try {
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), where, params, null, null, null);
			return cursor.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
		return 0;
	}
	
	public boolean exists (String where, String[] params) {
		boolean result = false;
		try {
			int count = this.count(where, params);
			if (count > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			cursor.close();
		}
		return result;
	}
	
	abstract protected String tableName ();
	abstract protected String[] tableColumns ();
	abstract protected String createSql ();
	abstract protected String upgradeSql ();
	
	protected class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(createSql());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(upgradeSql());
			onCreate(db);
		}
	}
}