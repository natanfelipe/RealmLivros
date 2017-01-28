package br.com.realmlivros.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import br.com.realmlivros.view.ActivityFoto;

/**
 * Created by Natan_Felipe on 28/01/2017.
 */

public class Utils {


    public static void permissoesFoto(ActivityFoto activityFoto, int PERMISSAO) {
        if(ContextCompat.checkSelfPermission(activityFoto, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(activityFoto,Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
                ActivityCompat.requestPermissions(activityFoto,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO);
            }
        }
    }
}
