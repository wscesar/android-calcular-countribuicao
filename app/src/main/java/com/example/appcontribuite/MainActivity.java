package com.example.appcontribuite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcontribuite.data.DBHelper;
import com.example.appcontribuite.model.Contribuinte;

import java.text.NumberFormat;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    DBHelper db = new DBHelper(this);
    Spinner inputSexo;
    EditText inputCpf;
    EditText inputNome;
    EditText inputSalario;
    EditText inputContribuicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] generos = {"Masculino", "Feminino"};
        inputSexo = findViewById(R.id.sexo);

        ArrayAdapter<String> arrayAdapter =
            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, generos);

        inputSexo.setAdapter(arrayAdapter);
        inputSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Button outputSexo = findViewById(R.id.outputSexo);
                String sexo = generos[position];

                switch (sexo){
                    case "Masculino" :
                        outputSexo.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;

                    case "Feminino" :
                        outputSexo.setBackgroundColor(getResources().getColor(R.color.pink));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showContribuintes();

        Button saveButton =  findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputCpf = findViewById(R.id.cpf);
                inputNome = findViewById(R.id.nome);
                inputSalario = findViewById(R.id.salario);
                inputContribuicao = findViewById(R.id.contribuicao);

                String cpf = inputCpf.getText().toString().trim();
                String nome = inputNome.getText().toString().trim();
                String salario = inputSalario.getText().toString().trim();
                String contribuicao = inputContribuicao.getText().toString().trim();

                if(nome.isEmpty())
                    Toast.makeText(getApplicationContext(), "Informe seu Nome", Toast.LENGTH_SHORT).show();
                else if(cpf.isEmpty())
                    Toast.makeText(getApplicationContext(), "Informe seu CPF", Toast.LENGTH_SHORT).show();
                else if(salario.isEmpty())
                    Toast.makeText(getApplicationContext(), "Informe seu Salário", Toast.LENGTH_SHORT).show();
                else if(contribuicao.isEmpty())
                    Toast.makeText(getApplicationContext(), "Informe sua Contribuição", Toast.LENGTH_SHORT).show();
                else
                    saveContribuinte();
            }
        });
    }

    private void showContribuintes() {
        List<Contribuinte> contribuintes = db.selectAll();
        for (Contribuinte c : contribuintes){
            Log.d("CONTRIBUINTE", c.getId() + " : " + c.getNome() + " : " + c.getCpf()+ " : " + c.getSalario()+ " : " + c.getContribuicao() );
        }
    }
    private void saveContribuinte() {
        inputCpf = findViewById(R.id.cpf);
        inputNome = findViewById(R.id.nome);
        inputSexo = findViewById(R.id.sexo);
        inputSalario = findViewById(R.id.salario);
        inputContribuicao = findViewById(R.id.contribuicao);

        String cpf = inputCpf.getText().toString().trim();
        String nome = inputNome.getText().toString().trim();
        String sexo = inputSexo.getSelectedItem().toString().trim();
        Float salario = Float.parseFloat( inputSalario.getText().toString().trim() );
        int contribuicao = Integer.parseInt( inputContribuicao.getText().toString().trim() );

        Contribuinte contribuinte = new Contribuinte();
        contribuinte.setCpf(cpf);
        contribuinte.setNome(nome);
        contribuinte.setSexo(sexo);
        contribuinte.setSalario(salario);
        contribuinte.setContribuicao(contribuicao);

        db.insert(contribuinte);
        calcularContribuicao(contribuicao, salario);
        showContribuintes();
        clearInputs();

        Toast.makeText(MainActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
    }

    private void clearInputs(){
        inputCpf = findViewById(R.id.cpf);
        inputNome = findViewById(R.id.nome);
        inputSalario = findViewById(R.id.salario);
        inputContribuicao = findViewById(R.id.contribuicao);

        inputCpf.setText("");
        inputNome.setText("");
        inputSalario.setText("");
        inputContribuicao.setText("");
    }

    private void calcularContribuicao(int contribuicao, float salario){
        TextView outputContribuicao = findViewById(R.id.outputContribuicao);
        double valorContribuicao = salario * contribuicao * 0.01;

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String currency = currencyFormat.format(valorContribuicao);
        outputContribuicao.setText("Valor de Contribuição: " + currency);
    }








}
