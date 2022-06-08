package br.com.dlweb.maternidade_ads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.dlweb.maternidade_ads.medico.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainFragment()).commit();
        }
    }
}