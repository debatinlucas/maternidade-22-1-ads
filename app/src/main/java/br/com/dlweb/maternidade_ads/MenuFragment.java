package br.com.dlweb.maternidade_ads;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MenuFragment extends Fragment {

    public MenuFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mae:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.dlweb.maternidade_ads.mae.MainFragment()).commit();
                break;
            case R.id.menu_medico:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.dlweb.maternidade_ads.medico.MainFragment()).commit();
                break;
            case R.id.menu_bebe:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.dlweb.maternidade_ads.bebe.MainFragment()).commit();
                break;
            case R.id.menu_camera:
                Intent camera = new Intent(getActivity(), CameraActivity.class);
                startActivity(camera);
                break;
            case R.id.menu_sensor:
                Intent sensor = new Intent(getActivity(), SensorActivity.class);
                startActivity(sensor);
                break;
            case R.id.menu_compartilhar:
                // Adroid Sharesheet
                Intent content = new Intent();
                content.setAction(Intent.ACTION_SEND);
                content.putExtra(Intent.EXTRA_TEXT, "Mensagem de teste!");
                content.setType("text/plain");
                Intent share = Intent.createChooser(content, null);
                startActivity(share);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}