package com.example.reto2.ui.servicios;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reto2.R;
import com.example.reto2.ServiceFormActivity;
import com.example.reto2.databinding.FragmentServiciosBinding;
import com.example.reto2.datos.ApiOracleServices;
import com.example.reto2.modelos.Servicio;

import java.util.ArrayList;

public class ServiciosFragment extends Fragment {
    private GridView gridView;
    private ProgressBar progressBar;
    private ApiOracleServices api;
    private ArrayList<Servicio> servicios = new ArrayList<>();
    private FragmentServiciosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_servicios,container,false);

        try{
            api = new ApiOracleServices(root.getContext());
            gridView = (GridView) root.findViewById(R.id.gridServices);
            progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

            api.getAllServiceFragment(gridView, progressBar);
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(getContext(), ServiceFormActivity.class);
                intent.putExtra("name","SERVICIOS");
                getActivity().startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}