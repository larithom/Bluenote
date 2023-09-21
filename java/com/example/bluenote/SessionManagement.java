package com.example.bluenote;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String USUARIO_KEY = "session_usuario";
    String EMAIL_KEY = "session_email";
    String SENHA_KEY = "session_senha";
    Boolean atualizar = false;

    public SessionManagement(Context context){
        sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void saveSession(Usuario u) {
        editor.putString(USUARIO_KEY, u.usuario).commit();
        editor.putString(EMAIL_KEY, u.email).commit();
        editor.putString(SENHA_KEY, u.senha).commit();
    }

    public String getSession() {
        return sp.getString(USUARIO_KEY, null);
    }

    public String getSessionMeia() {
        String dados =  sp.getString(EMAIL_KEY, null)+", "+sp.getString(USUARIO_KEY, null);
        return dados;
    }

    public void removeSession() {
        editor.putString(USUARIO_KEY, null).commit();
        editor.putString(EMAIL_KEY, null).commit();
        editor.putString(SENHA_KEY, null).commit();
    }
}
