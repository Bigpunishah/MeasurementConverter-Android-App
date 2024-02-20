package com.example.measurementconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, AdapterView.OnItemSelectedListener {

    private TextView starterConversionTextView;
    private TextView endingConversionTextView;
    private TextView conversionOutputTextView;
    private EditText inputValueEditText;
    private Spinner conversionSpinner;

    private String inputValueString = "";

    private final int CONVERT_M_TO_K = 0;
    private final int CONVERT_K_TO_M = 1;
    private final int CONVERT_I_TO_C = 2;
    private final int CONVERT_C_TO_I = 3;

    private int currentConversion = CONVERT_M_TO_K;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starterConversionTextView = (TextView) findViewById(R.id.starterConversionTextView);
        endingConversionTextView = (TextView) findViewById(R.id.endingConversionTextView); //This one is to update the text for the output label on spinner
        conversionOutputTextView = (TextView) findViewById(R.id.conversionOutputTextView); //This one is the actual value of result.
        inputValueEditText = (EditText) findViewById(R.id.inputValueEditText);
        conversionSpinner = (Spinner) findViewById(R.id.conversionSpinner);




        //this code sets up a Spinner in an Android application, populating it with data from a
        //string array resource and using a simple layout for displaying the items in the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.conversion_split_array, android.R.layout.simple_spinner_item);
        conversionSpinner.setAdapter(adapter);

        conversionSpinner.setOnItemSelectedListener(this);

        inputValueEditText.setOnEditorActionListener(this);


    }//End onCreate


//    This is for the inputValueEditText to have an action listener. This will call the function when the action is performed
//    Which is noticed by the actionId
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay(); //This is called when the user is done or not specified.
        }
        return false;
    }


    public void calculateAndDisplay(){
        inputValueString = inputValueEditText.getText().toString();
        float inputValue;

        if(inputValueString.equals("")){
            inputValue = 0;
        } else {
            inputValue = Float.parseFloat(inputValueString);
        }

        //This will be the value displayed at the end.
        float conversionOutput = 0;

        //Now checking to see where the spinner is
        if(currentConversion == CONVERT_M_TO_K){
            conversionOutput = inputValue * 1.6093f;

        } else if(currentConversion == CONVERT_K_TO_M){
            conversionOutput = inputValue * .6214f;

        } else if(currentConversion == CONVERT_I_TO_C){
            conversionOutput = inputValue * 2.54f;

        } else if(currentConversion == CONVERT_C_TO_I){
            conversionOutput = inputValue * .3937f;

        }

        //Using number formatter because it will not let me "SetText" with the float by itself.
        NumberFormat formatNum = NumberFormat.getInstance();
        conversionOutputTextView.setText(formatNum.format(conversionOutput));

    }


    //For the spinner on item action listener
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        currentConversion = position;

        //Change the name of label for the correct position of spinner.
        //Setting text to the resource (R.string)
        if(position == 0){
            starterConversionTextView.setText(R.string.milesString);
            endingConversionTextView.setText(R.string.kilometersString);

        } else if(position == 1){
            starterConversionTextView.setText(R.string.kilometersString);
            endingConversionTextView.setText(R.string.milesString);

        } else if(position == 2){
            starterConversionTextView.setText(R.string.inchesString);
            endingConversionTextView.setText(R.string.centimetersString);

        } else {
            starterConversionTextView.setText(R.string.centimetersString);
            endingConversionTextView.setText(R.string.inchesString);
        }

        calculateAndDisplay();
    }
    //For the spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
}//End mainActivity