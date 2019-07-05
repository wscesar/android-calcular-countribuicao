package com.example.appcontribuite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appcontribuite.model.Contribuinte;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "db";
    public static final String TABLE = "tbl_contribuintes";

    public static final  String COL_ID = "id";
    public static final  String COL_NOME = "nome";
    public static final  String COL_CPF = "cpf";
    public static final  String COL_SEXO = "sexo";
    public static final  String COL_SALARIO = "salario";
    public static final  String COL_CONTRIBUICAO = "contribuicao";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "create table " + TABLE + "("
                + COL_ID + " integer primary key,"
                + COL_NOME + " text,"
                + COL_CPF + " text,"
                + COL_SEXO + " text,"
                + COL_SALARIO + " float,"
                + COL_CONTRIBUICAO + " integer);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE);
        onCreate(db);
    }


    public void insert(Contribuinte contribuinte) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( COL_NOME, contribuinte.getNome() );
        values.put( COL_CPF, contribuinte.getCpf() );
        values.put( COL_SEXO, contribuinte.getSexo() );
        values.put( COL_SALARIO, contribuinte.getSalario() );
        values.put( COL_CONTRIBUICAO, contribuinte.getContribuicao() );

        db.insert(TABLE, null, values);
    }



    public List<Contribuinte> selectAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contribuinte> contribuinteList = new ArrayList<>();
        String sql = "select * from " + TABLE;

        Cursor c = db.rawQuery(sql, null);

        if ( c.moveToFirst() ) {
            do {
                Contribuinte contribuinte = new Contribuinte();
                contribuinte.setId(Integer.parseInt( c.getString(c.getColumnIndex(COL_ID)) ));
                contribuinte.setNome( c.getString(c.getColumnIndex(COL_NOME)) );
                contribuinte.setCpf( c.getString(c.getColumnIndex(COL_CPF)) );
                contribuinte.setSexo( c.getString(c.getColumnIndex(COL_SEXO)) );
                contribuinte.setSalario( c.getFloat(c.getColumnIndex(COL_SALARIO)) );
                contribuinte.setContribuicao( c.getInt(c.getColumnIndex(COL_CONTRIBUICAO)) );

                contribuinteList.add(contribuinte);
            } while ( c.moveToNext() );

        }

        return contribuinteList ;
    }
}
