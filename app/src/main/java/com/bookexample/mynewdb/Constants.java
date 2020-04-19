package com.bookexample.mynewdb;

public class Constants {

    //db name
    public static final  String DB_NAME = "PERSON_INFO_DB";
    //db version
    public static final int DB_VERSION = 1;
    //db table
    public static final String TABLE_NAME = "PERSON_INFO_TABLE";
    //table columns
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_AGE = "AGE";
    public static final String C_PHONE = "PHONE";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_ADD_TIMESTEMP = "ADD_TIMESTEMP";
    public static final String C_UPDATE_TIMESTEMP = "UPDATE_TIMESTEMP";

    //CREATE 쿼리 FOR TABLE-- 실제 테이블이 생성되는 구문으로 중요
    // 오타 및 쿼리 구문 철저확인 필수, 공백, 대문자...
    public static final String  CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_AGE + " TEXT,"
            + C_PHONE + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_ADD_TIMESTEMP + " TEXT,"
            + C_UPDATE_TIMESTEMP + " TEXT"
            + ");";

    //

}
