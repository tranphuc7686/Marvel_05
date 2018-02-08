package com.framgia.mavel.model;

/**
 * Created by Admin on 31/01/2018.
 */

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.framgia.mavel.bean.HeroMarvel;

import java.util.ArrayList;

/**
 * Created by Admin on 26/01/2018.
 */

public class SqliteHelper {
    private SQLiteDatabase mSqLiteDatabase;
    private AppCompatActivity mAppCompatActivity;
    private ContentValues contentValues;
    public static final String TABLE_NAME = "HEROFAV";
    public static final String IDHERO_COLUMN = "IDHERO";
    public static final String NAMEHERO_COLUMN = "NAMEHERO";
    public static final String IMAGEHERO_COLUMN = "IMAGEHERO";
    public static final String MOTAHERO_COLUMN = "MOTAHERO";
    public static final String ISFAV_COLUMN = "ISFAV";
    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    public final int EXTERNAL_REQUEST = 138;

    public SqliteHelper(AppCompatActivity mAppCompatActivity) {
        this.mAppCompatActivity = mAppCompatActivity;
    }

    public void creatDatabase() {
        Context ctx = this.mAppCompatActivity.getBaseContext();
        String DATABASE_PATH = ctx.getFilesDir().getPath() + "/HeroMarvel.db";
        System.out.println(DATABASE_PATH);
        this.mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
        this.mSqLiteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }


    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        System.out.println(version + " version");
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                mAppCompatActivity.requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mAppCompatActivity.getBaseContext(), perm));
    }

    public void createTable() {
        mSqLiteDatabase.execSQL("drop table if exists HEROFAV");
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + IDHERO_COLUMN + " TEXT PRIMARY KEY," + NAMEHERO_COLUMN + " TEXT," + IMAGEHERO_COLUMN + " TEXT,+" + MOTAHERO_COLUMN + " TEXT," + ISFAV_COLUMN + " TEXT)";
        this.mSqLiteDatabase.execSQL(sql);
    }

    public void insertRecord(String valueIDHero) {
        contentValues = new ContentValues();
        contentValues.put(IDHERO_COLUMN, valueIDHero);
        System.out.println(mSqLiteDatabase);
        if (mSqLiteDatabase.insert(TABLE_NAME, null, contentValues) == -1) {
            Toast.makeText(mAppCompatActivity.getBaseContext(), "Lỗi insert", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(mAppCompatActivity.getBaseContext(), "Thành Công insert", Toast.LENGTH_SHORT).show();
    }

    public void insertLikeHero(String valueIDHero) {
        contentValues = new ContentValues();
        contentValues.put(ISFAV_COLUMN, "FAV");
        int isTrue = mSqLiteDatabase.update(TABLE_NAME, contentValues, IDHERO_COLUMN + "=?", new String[]{valueIDHero});
        if (isTrue == 0) {
            Toast.makeText(mAppCompatActivity.getBaseContext(), "Lỗi Hệ Thống ", Toast.LENGTH_SHORT).show();
            System.out.println("Like that bai");
        } else {
            Toast.makeText(mAppCompatActivity.getBaseContext(), "Like", Toast.LENGTH_SHORT).show();
            checkDataDatabase();
            System.out.println("Like thanh cong");
        }
    }

    public void insertDontLikeHero(String valueIDHero) {
        contentValues = new ContentValues();
        contentValues.put(ISFAV_COLUMN, "");
        int isTrue = mSqLiteDatabase.update(TABLE_NAME, contentValues, IDHERO_COLUMN + "=?", new String[]{valueIDHero});
        if (isTrue == 0) {
            Toast.makeText(mAppCompatActivity.getBaseContext(), "Lỗi Hệ Thống ", Toast.LENGTH_SHORT).show();
            System.out.println("Like that bai");
        } else {
            Toast.makeText(mAppCompatActivity.getBaseContext(), " Đã Bỏ Like", Toast.LENGTH_SHORT).show();
            checkDataDatabase();
        }
    }

    public void insertAllHero(ArrayList<HeroMarvel> heroMarvel) {
        contentValues = new ContentValues();
        for (HeroMarvel a : heroMarvel) {
            contentValues.put(IDHERO_COLUMN, a.getId());
            contentValues.put(NAMEHERO_COLUMN, a.getNameOfHero());
            contentValues.put(IMAGEHERO_COLUMN, a.getImageHero());
            contentValues.put(MOTAHERO_COLUMN, a.getDescriptionOfHero());
            contentValues.put(ISFAV_COLUMN, "");
            int id = (int) mSqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            if (id == -1) {
                mSqLiteDatabase.update(TABLE_NAME, contentValues, IDHERO_COLUMN + "=?", new String[]{a.getId()});
            }
        }
    }

    public void deleteRecord(String valueIDHero) {

        if (mSqLiteDatabase.delete(TABLE_NAME, IDHERO_COLUMN + "=?", new String[]{valueIDHero}) != 1) {
            Toast.makeText(mAppCompatActivity.getBaseContext(), "Lỗi insert", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(mAppCompatActivity.getBaseContext(), "Thành Công insert", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<HeroMarvel> getFavHero() {
        ArrayList<HeroMarvel> arrHeroMarvel = new ArrayList<HeroMarvel>();
        Cursor c = mSqLiteDatabase.query(TABLE_NAME, null, ISFAV_COLUMN + "=?", new String[]{"FAV"}, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            arrHeroMarvel.add(new HeroMarvel(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
            c.moveToNext();
        }
        c.close();
        return arrHeroMarvel;
    }

    public ArrayList<HeroMarvel> getAllHero() {
        ArrayList<HeroMarvel> arrHeroMarvel = new ArrayList<HeroMarvel>();
        Cursor c = mSqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            arrHeroMarvel.add(new HeroMarvel(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
            c.moveToNext();
        }
        c.close();
        return arrHeroMarvel;
    }

    public void deleteDataTable(String tableName) {
        this.mSqLiteDatabase.delete(tableName, null, null);
    }

    public void closeDatabase() {
        this.mSqLiteDatabase.close();
    }

    public void checkDataDatabase() {
        Cursor c = mSqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            System.out.println(c.getString(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getString(4) + "  Main chay");
            c.moveToNext();
        }
        System.out.println("--------------------------------------------------------------------------");
        c.close();
    }

}