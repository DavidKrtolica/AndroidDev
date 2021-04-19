package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText x;
    private EditText y;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = findViewById(R.id.editTextNumberDecimal);
        y = findViewById(R.id.editTextNumberDecimal2);
        result = findViewById(R.id.textView4);

    }

    public int get_x() {
        int number_x = 0;
        if(String.valueOf(x.getText()).equals("")) {
            number_x = 0;
        } else {
            number_x = Integer.parseInt(String.valueOf(x.getText()));
        }
        return number_x;
    }

    public int get_y() {
        int number_y = 0;
        if(String.valueOf(y.getText()).equals("")) {
            number_y = 0;
        } else {
            number_y = Integer.parseInt(String.valueOf(y.getText()));
        }
        return number_y;
    }

    public void addition(View view) {
        int number_x = get_x();
        int number_y = get_y();
        int result_number = (number_x + number_y);
        result.setText(String.valueOf(result_number));
    }

    public void subtraction(View view) {
        int number_x = get_x();
        int number_y = get_y();
        int result_number = (number_x - number_y);
        result.setText(String.valueOf(result_number));
    }

    public void multiplication(View view) {
        int number_x = get_x();
        int number_y = get_y();
        int result_number = (number_x * number_y);
        result.setText(String.valueOf(result_number));
    }

    public void division(View view) {
        int number_x = get_x();
        int number_y = get_y();
        if(number_y == 0) {
            result.setText("Cannot divide by zero!");
        } else {
            int result_number = (number_x / number_y);
            result.setText(String.valueOf(result_number));
        }
    }

}