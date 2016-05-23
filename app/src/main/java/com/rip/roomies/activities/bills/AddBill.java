package com.rip.roomies.activities.bills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;

import java.text.DecimalFormat;

public class AddBill extends GenericActivity {

    private EditText name;
    private EditText description;
    private EditText amount;
    private Button submitNewBill;

    private final int RESULT_CODE_ADD_BILL = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        //link xml objects to java
        name = (EditText) findViewById(R.id.addBillName);
        description = (EditText) findViewById(R.id.addBillDescription);
        amount = (EditText) findViewById(R.id.addBillAmount);
        submitNewBill = (Button) findViewById(R.id.submitNewBill);


        submitNewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedAmount;

                //if parseARgs returns false, means user entered in something wrong.
                if (!parseArgs(name.getText().toString(), description.getText().toString(),
                        amount.getText().toString(), amount)) {
                    return;
                }

                //otherwise amount is a valid float, so keep going
                //pass the 3 fields back to activities.bills.Bills
                Intent intent = new Intent();
                intent.putExtra("Key_New_Name", name.getText().toString());
                intent.putExtra("Key_New_Description", description.getText().toString());
                intent.putExtra("Key_New_Amount", amount.getText().toString());
                setResult(RESULT_CODE_ADD_BILL, intent);
                finish();
            }

        });
    }

    /**
     *
     *
     * @param name
     * @param description
     * @param amount
     * @return true if parseArgs failed, ie the user didnt enter in something.
     */

    public boolean parseArgs(String name, String description, String amount, EditText etAmount) {
        float tempFloat;
        DecimalFormat df = new DecimalFormat("#.00");

        //check the name first.
        if (name == "" || description == "" || amount == "") {
            //the number the entered for the amount had non-numeric chars
            Toast.makeText(getApplicationContext(), "Make sure all fields are filled.",
                    Toast.LENGTH_LONG).show();
        }

        try {
            tempFloat = Float.valueOf(amount);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Please enter in a valid number for the amount.",
                    Toast.LENGTH_LONG).show();
            etAmount.setText("");
            return false;
        }

        return true;
    }

}
