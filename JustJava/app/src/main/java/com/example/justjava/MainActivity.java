package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameEditText = findViewById(R.id.name_edit_text);
        String nameOfCustomer = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, nameOfCustomer);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for "+ nameOfCustomer);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this, "You Cannot Have More Than 100 Cup Of Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "You Cannot Have Less Than 1 Cup Of Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // price of 1 cup
        int basePrice = 5;

        // if user wants a whippedCream the price would be +1
        if(addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // if user wants a chocolate the price would be +2
        if(addChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity*basePrice;
    }

    /**
     * It creates the summary of the order.
     * addWhippedCream variable used to check if checkBox is checked or not.
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String addCustomerName) {
        String priceMessage = "Name: " + addCustomerName;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate?" + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $ " +price;
        priceMessage +=  "\nThank You!";
        return priceMessage;
    }
}