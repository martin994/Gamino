package ar.edu.utn.frsf.isi.dam.gamino;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;

public class AdaptadorIntereses extends RecyclerView.Adapter<AdaptadorIntereses.ViewHolderIntereses> {


    public List<Interes> listaIntereses;


    public AdaptadorIntereses(List<Interes> listaIntereses) {
        this.listaIntereses = listaIntereses;
    }

    @NonNull
    @Override
    public ViewHolderIntereses onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista=LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.contenedor_interes,viewGroup,false);

        return new ViewHolderIntereses( vista );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIntereses viewHolderIntereses, int i) {

       viewHolderIntereses.tvNombreInteres.setText( listaIntereses.get( i ).getNombreInteres() );
       viewHolderIntereses.tvDescripcionInteres.setText( listaIntereses.get( i ).getDescripcionInteres() );
        //Verificar bien como traer FireBase las imagenes para saber como se setea
       //viewHolderIntereses.imgInteres.setImageBitmap( listaIntereses.get( i ).getIconoInteres() );


    }

    @Override
    public int getItemCount() {
        return listaIntereses.size();
    }

    public static class ViewHolderIntereses extends RecyclerView.ViewHolder {

        private TextView tvNombreInteres;
        private TextView tvDescripcionInteres;
        private ImageView imgInteres;

        public ViewHolderIntereses(@NonNull View itemView) {
            super( itemView );
            tvNombreInteres=(TextView)itemView.findViewById( R.id.contenedorInteresesTVNombre);
            tvDescripcionInteres=(TextView)itemView.findViewById( R.id.contenedorInteresTVDescripcion );
            imgInteres=(ImageView)itemView.findViewById( R.id.contenedorInteresesImg);
        }
    }
}
