package br.com.realmlivros.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.realmlivros.R;
import br.com.realmlivros.model.Livro;

/**
 * Created by Natan_Felipe on 26/01/2017.
 */

public class ListaLivrosAdapter extends ArrayAdapter<Livro> {

    private final Context context;
    private final List<Livro> elementos;

    public ListaLivrosAdapter(Context context, List<Livro> elementos) {
        super(context, R.layout.listview_item_livros, elementos);
        this.context = context;
        this.elementos = elementos;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listview_item_livros, parent, false);

        TextView txtTitulo = (TextView) rowView.findViewById(R.id.tv_titulo);
        TextView txtAutor = (TextView) rowView.findViewById(R.id.tv_autor);
        TextView txtAno = (TextView) rowView.findViewById(R.id.tv_ano);

        String titulo = elementos.get(position).getNome();
        String autor = elementos.get(position).getAutor();
        String ano = Integer.toString(elementos.get(position).getAno());

        txtTitulo.setText(titulo);
        txtAutor.setText(autor);
        txtAno.setText(ano);

        return rowView;
    }
}
