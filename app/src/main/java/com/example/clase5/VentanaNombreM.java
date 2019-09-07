package com.example.clase5;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class VentanaNombreM extends DialogFragment {

    private FragmentListener listener;

    public VentanaNombreM() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_ventana_nombre_m, null);
        builder.setView(v)
                .setTitle("Nombre del marcador: ")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText nombre = v.findViewById(R.id.nombre_marcador);
                        String a = nombre.getText().toString();
                        listener.ApplyText(a);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VentanaNombreM.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }

    public interface FragmentListener{
        void ApplyText(String name);
    }
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ventana_nombre_m, container, false);
    }
    */

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {
            listener = (FragmentListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException("must implement dialog listener");
        }
    }
}
