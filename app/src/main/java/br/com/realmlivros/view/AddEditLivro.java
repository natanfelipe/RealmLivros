package br.com.realmlivros.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    private EditText txtNome,txtAutor,txtAno;
    private String nome,autor,ano;
    private Button btnCadastrar,btnRemover;
    private Livro livro;
    private int id;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_livro);

        txtNome = (EditText) findViewById(R.id.et_nome);
        txtAutor = (EditText) findViewById(R.id.et_autor);
        txtAno = (EditText) findViewById(R.id.et_ano);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);

        livro = new Livro();

        Intent intentMain = getIntent();

        id = intentMain.getIntExtra("id", 0);
        final Realm realm = Realm.getDefaultInstance();

        if(id != 0){

            btnRemover = (Button) findViewById(R.id.btn_remover);

            livro = realm.where(Livro.class).equalTo("id",id).findFirst();

            txtAutor.setText(livro.getAutor());
            txtNome.setText(livro.getNome());
            txtAno.setText(String.valueOf(livro.getAno()));

            btnCadastrar.setText("Editar");

            btnRemover.setVisibility(View.VISIBLE);

            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ano = txtAno.getText().toString();
                    autor = txtAutor.getText().toString();
                    nome = txtNome.getText().toString();

                    verificaCamposObrigatorios();

                    if(!nome.equals("") && (!autor.equals("")) && (!ano.equals(""))){
                        realm.beginTransaction();
                        livro.setNome(nome);
                        livro.setAutor(autor);
                        livro.setAno(Integer.parseInt(ano));
                        realm.copyToRealm(livro);
                        realm.commitTransaction();
                        realm.close();

                        Toast.makeText(AddEditLivro.this, "Sucesso", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }
            });

            btnRemover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alerta = new AlertDialog.Builder(AddEditLivro.this);
                    alerta.setMessage("Deseja remover o livro "+livro.getNome()).
                    setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            realm.beginTransaction();
                            livro.deleteFromRealm();
                            realm.commitTransaction();
                            realm.close();

                            Toast.makeText(AddEditLivro.this, "Removido com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    alerta.setNegativeButton("Não",null);
                    alerta.show();


                }
            });
        }else {
            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nome = txtNome.getText().toString();
                    autor = txtAutor.getText().toString();
                    ano = txtAno.getText().toString();
                    verificaCamposObrigatorios();

                    if(!nome.equals("") && (!autor.equals("")) && (!ano.equals(""))){


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
