package br.com.realmlivros.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.realmlivros.R;
import br.com.realmlivros.model.Livro;
import io.realm.Realm;

public class AddEditLivro extends AppCompatActivity {

    EditText txtNome,txtAutor,txtAno;
    String nome,autor,ano;
    Button btnCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_livro);

        txtNome = (EditText) findViewById(R.id.et_nome);
        txtAutor = (EditText) findViewById(R.id.et_autor);
        txtAno = (EditText) findViewById(R.id.et_ano);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);

        final Realm realm = Realm.getDefaultInstance();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = txtNome.getText().toString();
                autor = txtAutor.getText().toString();
                ano = txtAno.getText().toString();
                verificaCamposObrigatorios();

                if(!nome.equals("") && (!autor.equals("")) && (!ano.equals(""))){

                    Livro livro = new Livro();

                    int proximoId=1;
                    if(realm.where(Livro.class).max("id")!=null)
                        proximoId = realm.where(Livro.class).max("id").intValue()+1;
                    livro.setId(proximoId);
                    livro.setNome(nome);
                    livro.setAutor(autor);
                    livro.setAno(Integer.parseInt(ano));
                    realm.beginTransaction();
                    realm.copyToRealm(livro);
                    realm.commitTransaction();
                    realm.close();

                    Toast.makeText(AddEditLivro.this, "Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    private void verificaCamposObrigatorios() {

        if(nome.equals("")){
            txtNome.setError("Favor informar o nome");
        }
        if(autor.equals("")){
            txtAutor.setError("Favor informar o autor");
        }
        if(ano.equals("")){
            txtAno.setError("Favor informar o ano");
        }
    }
}
