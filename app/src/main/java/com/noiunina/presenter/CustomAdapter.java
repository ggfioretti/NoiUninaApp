package com.noiunina.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noiunina.R;
import com.noiunina.model.GestoreRichieste;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    ArrayList<String> listTestoMessaggi;
    ArrayList<String> listMittenti;
    ArrayList<String> listUid;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;

    public CustomAdapter(Context context, ArrayList<String> listTestoMessaggi, ArrayList<String> listMittenti, ArrayList<String> listUid){

        this.context=context;
        this.listTestoMessaggi = listTestoMessaggi;
        this.listMittenti = listMittenti;
        this.listUid = listUid;

    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTesto;
        TextView textViewMittente;

        MessageInViewHolder(final View itemView) {
            super(itemView);
            textViewMittente = itemView.findViewById(R.id.chat_left_msg_text_view_MITTENTE);
            textViewTesto = itemView.findViewById(R.id.chat_left_msg_text_view);
        }

        void bind(int position) {
            String message = listTestoMessaggi.get(position);
            String mittente = listMittenti.get(position);
            textViewMittente.setText(mittente);
            textViewTesto.setText(message);
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTesto;
        TextView textViewMittente;

        MessageOutViewHolder(final View itemView) {
            super(itemView);
            textViewMittente = itemView.findViewById(R.id.chat_right_msg_text_view_MITTENTE);
            textViewTesto = itemView.findViewById(R.id.chat_right_msg_text_view);
        }

        void bind(int position) {
            String message = listTestoMessaggi.get(position);
            String mittente = listMittenti.get(position);
            textViewMittente.setText(mittente);
            textViewTesto.setText(message);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_item_left, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_item_right, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == MESSAGE_TYPE_IN) {
            ((MessageInViewHolder) holder).bind(position);
        } else {
            ((MessageOutViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return listTestoMessaggi.size();
    }

    @Override
    public int getItemViewType(int position) {
        GestoreRichieste sys = GestoreRichieste.getInstance();

        if(listUid.get(position).equals(sys.getUidStudente())) {
            return MESSAGE_TYPE_IN;
        }
        else{
            return MESSAGE_TYPE_OUT;
        }
    }

}

