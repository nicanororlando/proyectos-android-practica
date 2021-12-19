package example.com.tareasemana4.Mascotas;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import example.com.tareasemana4.BaseDatos.ConstructorMascotas;
import example.com.tareasemana4.Pojo.Mascota;
import example.com.tareasemana4.R;

public class AdaptadorMascota extends RecyclerView.Adapter<AdaptadorMascota.MascotaViewHolder>{

    ArrayList<Mascota> mascotas;
    private boolean mg;
    private final ItemClickListener clickListener;
    private Context context;

    //  Constructor para cuando llamemos a la clase pasarle la lista de contactos y el contexto.
    public AdaptadorMascota(ArrayList<Mascota> mascotas, ItemClickListener clickListener, Context context){
        this.mascotas = mascotas;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    //  Para darle vida a nuestro Layout CardView.
    /*  Retornamos nuestra vista al ViewHolder al constructor del ContactoViewHolder
    para poder tomar cada elemento del Layout. */
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MascotaViewHolder(v);
    }

    //  Este elemento se va invocando cada vez que se recorre la lista de contactos.
    //  Lo utilizamos para setear todos los datos accediendo al ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder mascotaViewHolder, int position) {
        ConstructorMascotas constructorMascotas = new ConstructorMascotas(context);
        Mascota mascota = mascotas.get(position);
        mascotaViewHolder.imgFoto.setImageResource(mascota.getFoto());
        mascotaViewHolder.tvNombreCV.setText(mascota.getNombre());
        mascotaViewHolder.tvLikes.setText(mascota.getLikes() + " Likes");
        //mascotaViewHolder.tvLikes.setText(constructorMascotas.obtenerLikesMascota(mascota) + " likes");

        mascotaViewHolder.itemView.setOnClickListener(v -> {
            clickListener.onItemClick(mascota);
        });

        mascotaViewHolder.btnLike.setOnClickListener(v -> {
            if (!mg){
                mascotaViewHolder.btnLike.setImageResource(R.drawable.like_lleno);
                mg = true;

                constructorMascotas.darLikeMascota(mascota);

                mascotaViewHolder.tvLikes.setText(constructorMascotas.obtenerLikesMascota(mascota) + " likes");
            }
            else {
                mascotaViewHolder.btnLike.setImageResource(R.drawable.like_vacio);
                mg = false;
            }
        });
    }

    @Override
    public int getItemCount() { //Cantidad de elementos de mi lista
        return mascotas.size();
    }

    //  Para darle vida a mis Views
    /*  Cualquier elemento estatico como este, siempre se va a accesar a partir del nombre de la clase
    que contiene este elemento, en este caso, ContactoAdaptador. */
    public static class MascotaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgFoto;
        private final TextView tvNombreCV;
        private final ImageView btnLike;
        private final TextView tvLikes;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFoto       = itemView.findViewById(R.id.imgFoto);
            tvNombreCV    = itemView.findViewById(R.id.tvNombreCV);
            btnLike       = itemView.findViewById(R.id.btnLike);
            tvLikes       = itemView.findViewById(R.id.tvLikes);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Mascota mascota);
    }
}
