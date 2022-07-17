package com.harsh1310.rakkktcharitr;

import android.content.Context;
import android.content.SharedPreferences;

public class stored_credentials {
    private static Context context;
    private static SharedPreferences sharedPreferences;
    private static stored_credentials instance;
    private  static String pref="TAS";
    public stored_credentials() {
        sharedPreferences=context.getSharedPreferences(pref,context.MODE_PRIVATE);
    }
    public  static stored_credentials getInstance(Context con)
    {

        context=con;
        if(instance==null)
        {
            instance =new stored_credentials();
        }

        return instance;
    }
    public void checkforlogin(String value)
    {
        sharedPreferences.edit().putString("login",value).apply();
    }
    public String getlogin()
    {
        String login=sharedPreferences.getString("login","");
        return login;
    }
    public void setid(String value)
    {
        sharedPreferences.edit().putString("userid",value).apply();
    }
    public String getuserid()
    {
        String getid=sharedPreferences.getString("userid","");
        return  getid;
    }
    public void checkforsignup(String val)
    {
       sharedPreferences.edit().putString("signup",val).apply();
    }
    public String getsignup()
    {
        return sharedPreferences.getString("signup","");
    }
   public void checkforotp(String val)
    {
        sharedPreferences.edit().putString("otpcheck",val).apply();
    }
    public  String getotpcheck()
    {
        return sharedPreferences.getString("otpcheck","");
    }

}
