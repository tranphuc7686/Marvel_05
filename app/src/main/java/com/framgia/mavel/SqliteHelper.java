package com.framgia.mavel;

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

import java.util.ArrayList;

/**
 * Created by Admin on 26/01/2018.
 */

public class SqliteHelper {
    private SQLiteDatabase mSqLiteDatabase;
    private AppCompatActivity mAppCompatActivity;
    private  ContentValues contentValues;

    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    public final int EXTERNAL_REQUEST = 138;

    public SqliteHelper(AppCompatActivity mAppCompatActivity) {
        this.mAppCompatActivity = mAppCompatActivity;
    }

    public void creatDatabase() {
        Context ctx = this.mAppCompatActivity.getBaseContext();
        String DATABASE_PATH = ctx.getFilesDir().getPath()+"/HeroMarvel.db";
        System.out.println(DATABASE_PATH);
        this.mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH,null);
        this.mSqLiteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);
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
    public void createTable(){
        mSqLiteDatabase.execSQL("drop table if exists HEROFAV");
        String sql="CREATE TABLE HEROFAV(IDHERO TEXT PRIMARY KEY,NAMEHERO TEXT,IMAGEHERO TEXT,MOTAHERO TEXT,ISFAV TEXT)";

        this.mSqLiteDatabase.execSQL(sql);
    }
    public void insertRecord(String valueIDHero){
        contentValues = new ContentValues();
        contentValues.put("IDHERO",valueIDHero);
        System.out.println(mSqLiteDatabase);
        if( mSqLiteDatabase.insert("HEROFAV",null,contentValues)== -1 ){
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Lỗi insert",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Thành Công insert",Toast.LENGTH_SHORT).show();


    }
    public void insertLikeHero(String valueIDHero){
        contentValues = new ContentValues();
        contentValues.put("ISFAV","FAV");
        int isTrue = mSqLiteDatabase.update("HEROFAV",contentValues,"IDHERO=?",new String[]{valueIDHero});
        if( isTrue== 0 ){
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Lỗi Hệ Thống ",Toast.LENGTH_SHORT).show();
            System.out.println("Like that bai");
        }
        else
        {
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Like",Toast.LENGTH_SHORT).show();
            System.out.println("Like thanh cong");
        }


    }
    public void insertAllHero(ArrayList<HeroMarvel> heroMarvel){
        contentValues = new ContentValues();

        for (HeroMarvel a : heroMarvel)
              {

                      contentValues.put("IDHERO",a.getId());
                      contentValues.put("NAMEHERO",a.getNameOfHero());
                      contentValues.put("IMAGEHERO",a.getImageHero());
                      contentValues.put("MOTAHERO",a.getDescriptionOfHero());
                  int id = (int) mSqLiteDatabase.insertWithOnConflict("HEROFAV",null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);

                  if (id == -1) {
                      mSqLiteDatabase.update("HEROFAV", contentValues, "IDHERO=?", new String[] {a.getId()});
                  }





        }








    }
    public void  deleteRecord(String valueIDHero){

        if( mSqLiteDatabase.delete("HEROFAV","IDHERO=?",new String[]{valueIDHero}) != 1 ){
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Lỗi insert",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Thành Công insert",Toast.LENGTH_SHORT).show();

    }
    public ArrayList<HeroMarvel> getFavHero()
    {   ArrayList<HeroMarvel> arrHeroMarvel = new ArrayList<HeroMarvel>();
        Cursor c = mSqLiteDatabase.query("HEROFAV",null,"ISFAV=?",new String[]{"FAV"},null,null,null);

        c.moveToFirst();

        while (!c.isAfterLast())
        {




            arrHeroMarvel.add(new HeroMarvel(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));

            c.moveToNext();



        }
        c.close();
        return arrHeroMarvel;
    }
    public ArrayList<HeroMarvel> getAllHero()
    {   ArrayList<HeroMarvel> arrHeroMarvel = new ArrayList<HeroMarvel>();
        Cursor c = mSqLiteDatabase.query("HEROFAV",null,null,null,null,null,null);
        c.moveToFirst();

        while (!c.isAfterLast())
        {



            arrHeroMarvel.add(new HeroMarvel(c.getString(0),c.getString(1),c.getString(2),c.getString(3),""));

            c.moveToNext();

        }
        c.close();
        return arrHeroMarvel;
    }
    public void deleteDataTable(String tableName){
        this.mSqLiteDatabase.delete(tableName,null,null);

    }
    public void closeDatabase(){
        this.mSqLiteDatabase.close();
    }

}