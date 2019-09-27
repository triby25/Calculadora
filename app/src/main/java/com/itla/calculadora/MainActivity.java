package com.itla.calculadora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvresultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvresultado = findViewById(R.id.tvresultado);

        ConstraintLayout constraintLayout = findViewById(R.id.wprincipal);
        for (int i = 0; i <= constraintLayout.getChildCount(); i++) {
            View child = constraintLayout.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String valorMostrado = tvresultado.getText().toString();
        if (valorMostrado.equals("Infinity")) {
            valorMostrado = "";
            tvresultado.setText("");
        }

        Button button = (Button) v;
        String valor = ((Button) v).getText().toString();

        if (valorMostrado.length()==0 && !valor.matches("[0-9]")) {
            return;
        }else {
            if (valorMostrado.length() > 0)
                if (!valorMostrado.substring(valorMostrado.length() - 1).matches("[0-9]") && !valor.matches("[0-9]"))
                    return;
        }

        switch (valor) {
            case "=":
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("js");
                Object result = null;
                try {
                    result = engine.eval(valorMostrado.replace("x", "*"));
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                if (result.toString().matches("\\d+") == false) {
                    tvresultado.setText(result.toString().replace(".0", ""));
                } else {
                    tvresultado.setText(result.toString());
                }

                break;
            case "C":
                tvresultado.setText("");
                break;
            default:
                valorMostrado += valor;
                tvresultado.setText(valorMostrado);
                break;
        }
    }
}
