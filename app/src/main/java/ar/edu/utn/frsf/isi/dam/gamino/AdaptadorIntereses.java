package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;

public class AdaptadorIntereses extends RecyclerView.Adapter<AdaptadorIntereses.ViewHolderIntereses>
        implements View.OnClickListener{

    private Context mContext;

    private List<Interes> listaIntereses;
    private View.OnClickListener listener;



    public AdaptadorIntereses(List<Interes> listaIntereses, Context mContext) {
        this.listaIntereses = listaIntereses;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolderIntereses onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista=LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.contenedor_interes,viewGroup,false);

        vista.setOnClickListener( this );
        return new ViewHolderIntereses( vista );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderIntereses viewHolderIntereses, int i) {

       viewHolderIntereses.tvNombreInteres.setText( listaIntereses.get( i ).getNombreInteres() );
       viewHolderIntereses.tvDescripcionInteres.setText( listaIntereses.get( i ).getDescripcionInteres() );

        Glide.with(  mContext).load( listaIntereses.get( i ).getIconoInteres() ).addListener( new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    viewHolderIntereses.progressBar.setVisibility( View.GONE );

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                viewHolderIntereses.progressBar.setVisibility( View.GONE );
                viewHolderIntereses.imgInteres.setVisibility( View.VISIBLE );
                return false;
            }
        } ).into( viewHolderIntereses.imgInteres );




    }

    @Override
    public int getItemCount() {
        return listaIntereses.size();
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



    public static class ViewHolderIntereses extends RecyclerView.ViewHolder {

        private TextView tvNombreInteres;
        private TextView tvDescripcionInteres;
        private ImageView imgInteres;
        private ProgressBar progressBar;

        public ViewHolderIntereses(@NonNull View itemView) {
            super( itemView );
            tvNombreInteres=(TextView)itemView.findViewById( R.id.contenedorInteresesTVNombre);
            tvDescripcionInteres=(TextView)itemView.findViewById( R.id.contenedorInteresTVDescripcion );
            imgInteres=(ImageView)itemView.findViewById( R.id.contenedorInteresesImg);
            progressBar= itemView.findViewById( R.id.contenedorInteresProgress );


        }


    }
}
