package com.mickaelbrenoit.demo.database;

public class Names {

    public static final String DATABASE_NAME = "demo";

    public static final String TABLE_NAME_USER = "users";
    public static final String PRIMARY_KEY_USER = "id";
    public static final String FIELD_NAME_USER = "name";
    public static final String FIELD_USERNAME_USER = "username";
    public static final String FIELD_EMAIL_USER = "email";
    public static final String FIELD_PASSWORD_USER = "password";

    public static final String TABLE_NAME_POST = "posts";
    public static final String PRIMARY_KEY_POST = "id";
    public static final String FIELD_TITLE_POST = "title";
    public static final String FIELD_BODY_POST = "body";
    public static final String FOREIGN_KEY_USERID_POST = "userId";

    public static final String TABLE_NAME_ALBUM = "albums";
    public static final String PRIMARY_KEY_ALBUM = "id";
    public static final String FIELD_TITLE_ALBUM = "title";
    public static final String FOREIGN_KEY_USERID_ALBUM = "userId";
}
