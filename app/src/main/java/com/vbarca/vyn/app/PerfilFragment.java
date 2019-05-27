package com.vbarca.vyn.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vbarca.vyn.R;
import com.vbarca.vyn.model.Base;
import com.vbarca.vyn.model.Usuario;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences usuarioAlmacenado;

    private OnFragmentInteractionListener mListener;
    private String uid;
    private String user;
    private String pass;
    private String email;
    private String uidsala;

    EditText cambioUser;
    EditText cambioPass;
    EditText cambioPass2;
    EditText cambioSala;

    Button btGuardarUser;
    Button btGuardarSala;
    Button btCopiar;

    Base base;

    View view;
    private Usuario usuario;
    private SharedPreferences.Editor editor;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        usuarioAlmacenado = getActivity().getSharedPreferences("controlUsuario", Context.MODE_PRIVATE);
        editor = usuarioAlmacenado.edit();

        // Recogemos el usuario logeado.
        uid = usuarioAlmacenado.getString("uid","");
        user = usuarioAlmacenado.getString("user","");
        pass = usuarioAlmacenado.getString("pass","");
        email = usuarioAlmacenado.getString("email","");
        uidsala = usuarioAlmacenado.getString("uidsala","");

        usuario = new Usuario(pass,user,email,uid,uidsala);

        ///////////////////////////////////////////////////////////

        btCopiar = view.findViewById(R.id.btCopiarSala);
        btGuardarSala = view.findViewById(R.id.btGuardarUsuario);
        btGuardarUser = view.findViewById(R.id.btGuardarSala);

        btGuardarUser.setOnClickListener(this);
        btGuardarSala.setOnClickListener(this);
        btCopiar.setOnClickListener(this);

        base = new Base(getContext());

        cambioUser = view.findViewById(R.id.etCambioUsuario);
        cambioPass = view.findViewById(R.id.etCambioClave);
        cambioPass2 = view.findViewById(R.id.etConfirmarCLave);
        cambioSala = view.findViewById(R.id.etCambioSala);

        cambioUser.setText(user);
        cambioSala.setText(uidsala);

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btCopiarSala:
                ClipData clip = ClipData.newPlainText("Texto copiado al portapapeles", cambioSala.getText().toString());
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btGuardarUsuario:
                String usuario2 = cambioUser.getText().toString();
                String passs1 = cambioPass.getText().toString();
                String passs2 = cambioPass2.getText().toString();

                // Comprobacion de campos

                if(!usuario2.equals(""))
                    usuario.setUser(cambioUser.getText().toString());
                if(passs1.equals(passs2) && !passs1.equals("")) {
                    usuario.setPassword(cambioPass.getText().toString());
                }

                ////////////////////////////////////////////////////

                base.getDatabaseReference().child("Usuario").child(usuario.getUid()).setValue(usuario);
                limpiarCampos();
                modificarSharedPreferences();
                break;
            case R.id.btGuardarSala:
                String sala2 = cambioSala.getText().toString();
                if(sala2.length()>35)
                usuario.setUidSala(sala2);
                base.getDatabaseReference().child("Usuario").child(usuario.getUid()).setValue(usuario);
                modificarSharedPreferences();
                break;
            default:
                break;
        }
    }

    private void limpiarCampos() {
        cambioPass.setText("");
        cambioUser.setText(usuario.getUser());
        cambioPass2.setText("");
    }

    private void modificarSharedPreferences() {
        editor.putString("uid",usuario.getUid());
        editor.putString("user",usuario.getUser());
        editor.putString("pass",usuario.getPassword());
        editor.putString("email",usuario.getEmail());
        editor.putString("uidsala",usuario.getUidSala());
        editor.commit();
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
}
