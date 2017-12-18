package com.cp.app.checkpoint.utils;

import android.content.Context;

import com.cp.app.checkpoint.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ahmed Yehya on 14/12/2017.
 */

public class ValidationUtils {

    public static boolean isMobileNumValid (String mobNum)
    {
        Pattern pattern;
        Matcher matcher;
        String MOB_NUN_PATTERN = "0(10|12|11|15){1}[0-9]{8}";
        pattern = Pattern.compile(MOB_NUN_PATTERN);
        matcher = pattern.matcher(mobNum);
        return matcher.matches();
    }

    public static boolean isGenderValid(Context context, String gender)
    {
        return (!(gender.equals(context.getString(R.string.gender_no_selection))));
    }
}
