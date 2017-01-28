package br.com.realmlivros.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import br.com.realmlivros.R;
import br.com.realmlivros.model.Livro;
import br.com.realmlivros.utils.Utils;
import io.realm.Realm;

public class ActivityFoto extends AppCompatActivity {

    private Button btnFoto, btnSalvar;
    private ImageView img;
    public static final int IMG_GALERY = 1;
    private static final int PERMISSAO = 2;
    private Bitmap imagemGaleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        btnFoto = (Button) findViewById(R.id.btn_foto);
        btnSalvar = (Button) findViewById(R.id.btn_salvar);
        img = (ImageView) findViewById(R.id.img_foto);

        final Realm realm = Realm.getDefaultInstance();

        //Utils.permissoesFoto(ActivityFoto.this,PERMISSAO);
        if(ContextCompat.checkSelfPermission(ActivityFoto.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(ActivityFoto.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
                ActivityCompat.requestPermissions(ActivityFoto.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO);
            }
        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,IMG_GALERY);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                imagemGaleria.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                byte imagemBytes[]=outputStream.toByteArray();

                realm.beginTransaction();
                Livro livro = new Livro();
                livro.setFoto(imagemBytes);
                realm.commitTransaction();
                realm.close();

                Toast.makeText(ActivityFoto.this, "Foto armazenada no BD", Toast.LENGTH_SHORT).show();
                img.setImageBitmap(imagemGaleria);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == IMG_GALERY){
            Uri image = data.getData();
            String[] caminhoArquivo = {MediaStore.Images.Media.DATA};
            Cursor c= getContentResolver().query(image,caminhoArquivo,null,null,null);
            c.moveToFirst();
            int indiceColuna = c.getColumnIndex(caminhoArquivo[0]);
            String caminhoFoto = c.getString(indiceColuna);
            c.close();
            imagemGaleria = (BitmapFactory.decodeFile(caminhoFoto));
            //img.setImageBitmap(imagemGaleria);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSAO){
            if(grantResults.length>0
                    && grantResults[0]== PackageManager.PERMISSION_GRANTED){

            } else {

            }

            return;
        }
    }
}
