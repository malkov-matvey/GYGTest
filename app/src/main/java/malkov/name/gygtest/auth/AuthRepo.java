package malkov.name.gygtest.auth;

import android.content.Context;

public class AuthRepo {

    public String authKey(final Context context) {
        //some shared pref or keystore stuff to retrieve authKey
        /*String authKey = context.getSharedPreferences("default", Context.MODE_PRIVATE)
                .getString("auth_key", null);*/

        return "23424AFA2234AFAAfA44444";
    }
}
