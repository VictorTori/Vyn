package com.vbarca.vyn.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vbarca.vyn.R;
import com.vbarca.vyn.adapter.AdapterInicio;
import com.vbarca.vyn.model.Base;
import com.vbarca.vyn.model.Calendario;
import com.vbarca.vyn.model.Fotos;
import com.vbarca.vyn.model.Item;
import com.vbarca.vyn.model.Lista;
import com.vbarca.vyn.model.MenuSemanal;
import com.vbarca.vyn.model.Usuario;
import com.vbarca.vyn.register.Login;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView lvLista;
    View view;
    ArrayList<Item> items;
    AdapterInicio adapter;
    Base base;
    String sala;
    SharedPreferences usuarioAlmacenado;
    AlertDialog dialog;
    Lista lista;
    Calendario calendario;
    MenuSemanal menuSemanal;
    Fotos fotos;
    Item item2;


    private OnFragmentInteractionListener mListener;
    private Button btAnadirLista;
    private EditText etTarea;

    public InicioFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        base = new Base(getContext());
        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        usuarioAlmacenado = getActivity().getSharedPreferences("controlUsuario", Context.MODE_PRIVATE);
        sala = usuarioAlmacenado.getString("uidsala","");

        lvLista = view.findViewById(R.id.listaInicio);
        lvLista.setOnItemClickListener(this);

        items = new ArrayList<>();
        adapter = new AdapterInicio(getActivity(), items);
        lvLista.setAdapter(adapter);
        registerForContextMenu(lvLista);

        refrescarLista(base);
        refrescarMenuSemanal(base);
        refrescarCalendario(base);
        refrescarFotos(base);

        return view;
    }

    private void refrescarFotos(Base base) {
        base.getDatabaseReference().child("Fotos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Fotos item;
                for(DataSnapshot calendar : dataSnapshot.getChildren()){
                    item = calendar.getValue(Fotos.class);
                    if(items.contains(item))
                        continue;
                    if(item.getSala().equals(sala))
                        items.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void refrescarCalendario(Base base) {
        base.getDatabaseReference().child("Calendario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Calendario item;
                for(DataSnapshot calendar : dataSnapshot.getChildren()){
                    item = calendar.getValue(Calendario.class);
                    if(items.contains(item))
                        continue;
                    if(item.getSala().equals(sala))
                        items.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void refrescarLista(Base base) {
        base.getDatabaseReference().child("Lista").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Lista item;
                for(DataSnapshot lista : dataSnapshot.getChildren()){
                    item = lista.getValue(Lista.class);
                    if(items.contains(item))
                        continue;
                    if(item.getSala().equals(sala))
                        items.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void refrescarMenuSemanal(Base base) {
        base.getDatabaseReference().child("MenuSemanal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                MenuSemanal item;
                for(DataSnapshot menu : dataSnapshot.getChildren()){
                    item = menu.getValue(MenuSemanal.class);
                    if(items.contains(item))
                        continue;
                    if(item.getSala().equals(sala))
                        items.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_contextual_borra_editar,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int i = info.position;

        switch (item.getItemId()) {
            case R.id.action_eliminar_inicio:
                if(items.get(i) instanceof Lista){
                    Lista lista = (Lista) items.get(i);
                    items.remove(items.get(i));
                    base.getDatabaseReference().child("Lista").child(lista.getUid()).removeValue();
                } else if (items.get(i) instanceof Calendario) {
                    Calendario calendario = (Calendario) items.get(i);
                    items.remove(items.get(i));
                    base.getDatabaseReference().child("Calendario").child(calendario.getUid()).removeValue();
                } else if (items.get(i) instanceof MenuSemanal) {
                    MenuSemanal menuSemanal = (MenuSemanal) items.get(i);
                    items.remove(items.get(i));
                    base.getDatabaseReference().child("MenuSemanal").child(menuSemanal.getUid()).removeValue();
                } else if (items.get(i) instanceof Fotos) {
                    Fotos fotos = (Fotos) items.get(i);
                    items.remove(items.get(i));
                    base.getDatabaseReference().child("Fotos").child(fotos.getUid()).removeValue();
                }
                adapter.notifyDataSetChanged();
                return true;
            case R.id.action_renombrar:
                item2 = items.get(i);
                crearDialog();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void crearDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.CustomDialog);
        View mView = getLayoutInflater().inflate(R.layout.lista_anadir,null);
        mBuilder.setView(mView);

        btAnadirLista = mView.findViewById(R.id.btAnadir_lista);
        btAnadirLista.setOnClickListener(this);
        etTarea = mView.findViewById(R.id.etTarea);

        dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent;

        if(items.get(i) instanceof Lista){
            Lista lista = (Lista) items.get(i);
            intent = new Intent(view.getContext(),VistaLista.class);
            intent.putExtra("lista", (Serializable) lista);
            intent.putExtra("titulo", lista.getTitulo());
            startActivity(intent);
        } else if (items.get(i) instanceof Calendario) {
            Calendario calendario = (Calendario) items.get(i);
            intent = new Intent(view.getContext(),VistaCalendario.class);
            intent.putExtra("calendario", (Serializable) calendario);
            intent.putExtra("titulo", calendario.getTitulo());
            startActivity(intent);
        } else if (items.get(i) instanceof MenuSemanal) {
            MenuSemanal menuSemanal = (MenuSemanal) items.get(i);
            intent = new Intent(view.getContext(),VistaMenuSemanal.class);
            intent.putExtra("menuSemanal", (Serializable) menuSemanal);
            intent.putExtra("titulo", menuSemanal.getTitulo());
            startActivity(intent);
        } else if (items.get(i) instanceof Fotos) {
            Fotos fotos = (Fotos) items.get(i);
            intent = new Intent(view.getContext(),VistaFotos.class);
            intent.putExtra("fotos", (Serializable) fotos);
            intent.putExtra("titulo", fotos.getTitulo());
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btAnadir_lista){
            if(item2 instanceof Lista){
                lista = (Lista) item2;
                lista.setTitulo(etTarea.getText().toString());
                base.getDatabaseReference().child("Lista").child(lista.getUid()).setValue(lista);
            } else if (item2 instanceof Calendario) {
                calendario = (Calendario) item2;
                calendario.setTitulo(etTarea.getText().toString());
                base.getDatabaseReference().child("Calendario").child(calendario.getUid()).setValue(calendario);
            } else if (item2 instanceof MenuSemanal) {
                menuSemanal = (MenuSemanal) item2;
                menuSemanal.setTitulo(etTarea.getText().toString());
                base.getDatabaseReference().child("MenuSemanal").child(menuSemanal.getUid()).setValue(menuSemanal);
            } else if (item2 instanceof Fotos) {
                fotos = (Fotos) item2;
                fotos.setTitulo(etTarea.getText().toString());
                base.getDatabaseReference().child("Fotos").child(fotos.getUid()).setValue(fotos);
            }
        }
        dialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
