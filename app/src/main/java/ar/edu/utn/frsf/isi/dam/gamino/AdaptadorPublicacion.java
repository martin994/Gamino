package ar.edu.utn.frsf.isi.dam.gamino;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;

public class AdaptadorPublicacion extends RecyclerView.Adapter<AdaptadorPublicacion.ViewHolderPublicacion> {

    List<Publicacion> publicacionLista;

    public AdaptadorPublicacion(List<Publicacion> publicacionLista) {
        this.publicacionLista = publicacionLista;
    }

    @NonNull
    @Override
    public ViewHolderPublicacion onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contenedor_publicacion,viewGroup,false);


        return new ViewHolderPublicacion(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPublicacion viewHolderPublicacion, int i) {
            viewHolderPublicacion.tituloPublicacion.setText( publicacionLista.get( i ).getTituloPublicacion() );
            viewHolderPublicacion.autorPublicacion.setText(  publicacionLista.get( i ).getEditor().getNombreusuario());
            viewHolderPublicacion.cuerpoPublicacion.setText( publicacionLista.get( i ).getCuerpoPublicacion() );
            //Verificar bien como traer FireBase las imagenes para saber como se setea
            viewHolderPublicacion.imagenDeLaPubliacion.setImageBitmap( publicacionLista.get( i ).getImagenPublicacion());

    }

    @Override
    public int getItemCount() {
        return publicacionLista.size();
    }

    public class ViewHolderPublicacion extends RecyclerView.ViewHolder {

        private TextView tituloPublicacion;
        private TextView autorPublicacion;
        private TextView cuerpoPublicacion;
        private ImageView imagenDeLaPubliacion;

        public ViewHolderPublicacion(@NonNull View itemView) {
            super( itemView );

                tituloPublicacion = itemView.findViewById( R.id.contenedorPulicacionTitulo );
                autorPublicacion= itemView.findViewById(R.id.contenedorPublicacionAutor);
                cuerpoPublicacion=itemView.findViewById( R.id.contenedorPublicacionCuerpo );
                imagenDeLaPubliacion=itemView.findViewById( R.id.contenedorPublicacionImg);


        }
    }
}
