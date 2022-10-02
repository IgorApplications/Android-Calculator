package com.iapp.calculator.view;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.splashscreen.SplashScreen;
import com.iapp.calculator.R;
import com.iapp.calculator.controller.Controller;
import com.iapp.calculator.model.WrongInputException;

import java.util.Arrays;

/**
 * The calculator graphical window on the Android operating system
 * @author Igor Ivanov
 * @version 1.0
 * */
public class CalculatorActivity extends AppCompatActivity {

    /** application business logic management */
    private Controller controller;
    /** fields for entering mathematical expressions */
    private EditText textField;

    /** launch application */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_calculator);

        initialize();
    }

    private void initialize() {
        controller = new Controller();
        textField = findViewById(R.id.text_field);
        textField.setShowSoftInputOnFocus(false);

        Button sin = findViewById(R.id.sin),
                tg = findViewById(R.id.tg),
                degree = findViewById(R.id.degree),
                backspace = findViewById(R.id.backspace),
                cos = findViewById(R.id.cos),
                ctg = findViewById(R.id.ctg),
                sqrt = findViewById(R.id.sqrt),
                division = findViewById(R.id.division),
                b7 = findViewById(R.id.b7),
                b8 = findViewById(R.id.b8),
                b9 = findViewById(R.id.b9),
                multiplication = findViewById(R.id.multiplication),
                b4 = findViewById(R.id.b4),
                b5 = findViewById(R.id.b5),
                b6 = findViewById(R.id.b6),
                minus = findViewById(R.id.minus),
                b1 = findViewById(R.id.b1),
                b2 = findViewById(R.id.b2),
                b3 = findViewById(R.id.b3),
                plus = findViewById(R.id.plus),
                brackets = findViewById(R.id.brackets),
                b0 = findViewById(R.id.b0),
                point = findViewById(R.id.point),
                calculation = findViewById(R.id.calculation);

        sin.setOnClickListener(view -> addAction(sin.getText().toString()));
        tg.setOnClickListener(view -> addAction(tg.getText().toString()));
        degree.setOnClickListener(view -> addAction(degree.getText().toString()));
        backspace.setOnClickListener(view -> removeCharacter());

        cos.setOnClickListener(view -> addAction(cos.getText().toString()));
        ctg.setOnClickListener(view -> addAction(ctg.getText().toString()));
        sqrt.setOnClickListener(view -> addAction(sqrt.getText().toString()));
        division.setOnClickListener(view -> addAction(division.getText().toString()));

        b7.setOnClickListener(view -> addAction(b7.getText().toString()));
        b8.setOnClickListener(view -> addAction(b8.getText().toString()));
        b9.setOnClickListener(view -> addAction(b9.getText().toString()));
        multiplication.setOnClickListener(view -> addAction(multiplication.getText().toString()));

        b4.setOnClickListener(view -> addAction(b4.getText().toString()));
        b5.setOnClickListener(view -> addAction(b5.getText().toString()));
        b6.setOnClickListener(view -> addAction(b6.getText().toString()));
        minus.setOnClickListener(view -> addAction(minus.getText().toString()));

        b1.setOnClickListener(view -> addAction(b1.getText().toString()));
        b2.setOnClickListener(view -> addAction(b2.getText().toString()));
        b3.setOnClickListener(view -> addAction(b3.getText().toString()));
        plus.setOnClickListener(view -> addAction(plus.getText().toString()));

        brackets.setOnClickListener(view -> addAction(defineBracket()));
        b0.setOnClickListener(view -> addAction(b0.getText().toString()));
        point.setOnClickListener(view -> addAction(point.getText().toString()));
        calculation.setOnClickListener(view -> countExpression());
    }

    private void countExpression() {
        var text = textField.getText().toString();
        String result;
        try {
            result = controller.calculate(text);
        } catch (WrongInputException e) {
            e.printStackTrace();
            Arrays.stream(e.getSuppressed()).forEach(Throwable::printStackTrace);
            textField.setText("");
            return;
        }
        textField.setText(result);
        textField.setSelection(result.length());
    }

    @SuppressLint("SetTextI18n")
    private void addAction(String action) {
        var text = textField.getText().toString();
        var caretStart = textField.getSelectionStart();
        var caretEnd = textField.getSelectionEnd();

        textField.setText(text.substring(0, caretStart) +
                action + text.substring(caretEnd));
        textField.setSelection(caretStart + action.length());
    }

    @SuppressLint("SetTextI18n")
    private void removeCharacter() {
        var text = textField.getText().toString();
        var caretStart = textField.getSelectionStart();
        var caretEnd = textField.getSelectionEnd();

        if (caretStart == caretEnd) {
            if (caretStart == 0) return;
            textField.setText(remove(text, caretStart - 1));
        } else {
            textField.setText(text.substring(0, caretStart) + text.substring(caretEnd));
        }
        textField.setSelection(caretStart - 1);
    }

    private String defineBracket() {
        var text = textField.getText();
        var opening = text.chars().filter(c -> c == '(').count();
        var closing = text.chars().filter(c -> c == ')').count();

        return opening > closing ? ")" : "(";
    }

    private String remove(String text, int index) {
        var arr = text.toCharArray();
        var builder = new StringBuilder();
        for (int i = 0; i  < arr.length; i++) {
            if (i != index) builder.append(arr[i]);
        }
        return builder.toString();
    }
}