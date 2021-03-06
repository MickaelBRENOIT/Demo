package com.mickaelbrenoit.demo.helper;

import java.util.Locale;

public class RequestCode {

    public static String LANGUAGE = Locale.getDefault().getLanguage();

    public static final String PUT_EXTRA_USER_LOGGED = "USER_LOGGED";
    public static final String PUT_EXTRA_TITLE_POST = "TITLE";
    public static final String PUT_EXTRA_OBJECT_POST = "OBJECT_POST";
    public static final String PUT_EXTRA_OBJECT_ALBUM = "OBJECT_ALBUM";

    public static final int RESULT_CODE_ADD_POST = 1;
    public static final int RESULT_CODE_MODIFY_POST = 2;
}
