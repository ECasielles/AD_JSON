package com.mercacortex.ad_json.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mercacortex.ad_json.R;
import com.mercacortex.ad_json.activity.CreacionJSONGSONActivity;
import com.mercacortex.ad_json.activity.ListaContactoActivity;
import com.mercacortex.ad_json.activity.ListaContactoGSONActivity;
import com.mercacortex.ad_json.activity.NoticiasActivity;
import com.mercacortex.ad_json.activity.PrimitivaActivity;
import com.mercacortex.ad_json.activity.PrimitivaRedActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btnPrimitiva);
        btn2 = (Button) findViewById(R.id.btnPrimitivaRed);
        btn3 = (Button) findViewById(R.id.btnContactos);
        btn4 = (Button) findViewById(R.id.btnContactosGSON);
        btn5 = (Button) findViewById(R.id.btnCreacionJSON);
        btn6 = (Button) findViewById(R.id.btnCreacionGSON);
        btn7 = (Button) findViewById(R.id.btnNoticias);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.btnPrimitiva:
                intent = new Intent(MainActivity.this, PrimitivaActivity.class);
                startActivity(intent);
                break;
            case R.id.btnPrimitivaRed:
                intent = new Intent(MainActivity.this, PrimitivaRedActivity.class);
                startActivity(intent);
                break;
            case R.id.btnContactos:
                intent = new Intent(MainActivity.this, ListaContactoActivity.class);
                startActivity(intent);
                break;
            case R.id.btnContactosGSON:
                intent = new Intent(MainActivity.this, ListaContactoGSONActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCreacionJSON:
                intent = new Intent(MainActivity.this, CreacionJSONGSONActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCreacionGSON:
                intent = new Intent(MainActivity.this, CreacionJSONGSONActivity.class);
                startActivity(intent);
                break;
            case R.id.btnNoticias:
                intent = new Intent(MainActivity.this, NoticiasActivity.class);
                startActivity(intent);
                break;

        }

    }
}