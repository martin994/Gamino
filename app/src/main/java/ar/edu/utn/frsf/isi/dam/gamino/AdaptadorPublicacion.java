package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;

public class AdaptadorPublicacion extends RecyclerView.Adapter<AdaptadorPublicacion.ViewHolderPublicacion>
        implements View.OnClickListener{

    private ArrayList<Publicacion> publicacionLista;
    private Interes interes;
    private Context mcontext;
    private View.OnClickListener listener;

    public AdaptadorPublicacion(ArrayList<Publicacion> publicacionLista, Interes interes,Context mcontext) {
        this.publicacionLista = publicacionLista;
         this.interes=interes;
         this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public ViewHolderPublicacion onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contenedor_publicacion,viewGroup,false);
        view.setOnClickListener( this );

        return new ViewHolderPublicacion(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPublicacion viewHolderPublicacion, int i) {
        if(interes.getIdInteres().equals(publicacionLista.get( i ).getidInteres())){
            viewHolderPublicacion.tituloPublicacion.setText( publicacionLista.get( i ).getTituloPublicacion() );
            viewHolderPublicacion.autorPublicacion.setText(  publicacionLista.get( i ).getEditor().getNombreusuario());
            viewHolderPublicacion.cuerpoPublicacion.setText( publicacionLista.get( i ).getSubtituloPublicacion());

            //viewHolderPublicacion.imagenDeLaPubliacion.setImageBitmap( publicacionLista.get( i ).getImagenPublicacion());

            Glide.with(mcontext).load(Uri.parse(interes.getIconoInteres())).addListener(new RequestListener<Drawable>() {

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                    return false;
                }
            }).into(viewHolderPublicacion.imagenDeLaPubliacion);
        }

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
    @Override
    public int getItemCount() {
        return publicacionLista.size();
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;

    }


    @Override
    public void onClick(View v) {

        if(listener!=null){

            listener.onClick( v );

        }

    }
}
