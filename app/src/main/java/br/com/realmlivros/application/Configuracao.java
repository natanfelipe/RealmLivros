package br.com.realmlivros.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Natan_Felipe on 25/01/2017.
 */

public class Configuracao extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
