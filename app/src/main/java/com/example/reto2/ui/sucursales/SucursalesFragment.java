package com.example.reto2.ui.sucursales;

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

import com.example.reto2.MapsActivity;
import com.example.reto2.MapsActivityShow;
import com.example.reto2.R;
import com.example.reto2.datos.ApiOracleBranches;
import com.example.reto2.modelos.Sucursal;

import java.util.ArrayList;

public class SucursalesFragment extends Fragment {

    private GridView gridView;
    private ProgressBar progressBar;
    private ApiOracleBranches api;
    private ArrayList<Sucursal> sucursales = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_sucursales,container,false);

        try{
            api = new ApiOracleBranches(root.getContext());
            gridView = (GridView) root.findViewById(R.id.gridViewSucursales);
            progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

            api.getAllSucursalFragment(gridView, progressBar);
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                Intent intent = new Intent(getContext(), MapsActivity.class);
                getActivity().startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}