/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.application2;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;
    /**
     * This method is called when the order button is clicked.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view) {
        if(quantity == 50) {
            Toast.makeText(this, "You cannot order more than 50 coffees!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "You cannot go below 1 coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    public void submitOrder(View view) {
       // int price = calculatePrice(quantity);
        //String priceMessage="Total: $" + price;
        //priceMessage = priceMessage + "\nThank You for ordering!";
        //displayMessage(priceMessage);
        submitOrderSummary();

    }
    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate){
        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        int base_price=5;
        if(hasWhippedCream)
            base_price = base_price + 1;
        if(hasChocolate)
            base_price = base_price + 2;
        int price = quantity * base_price;
        return price;
    }

    private void submitOrderSummary(){
        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        boolean hasChocolate = ChocolateCheckBox.isChecked();
        EditText text = (EditText)findViewById(R.id.Name_field);
        String value = text.getText().toString();
        String msg = "Name: " + value + "\nAdd whipped cream? "+
                hasWhippedCream + "\nAdd chocolate? "+
                hasChocolate + "\nQuantity: " +
                quantity + "\nTotal: $" +
                calculatePrice(quantity, hasWhippedCream, hasChocolate) + "\nThank You for ordering!";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Just coffee! order for " + value);
                intent.putExtra(Intent.EXTRA_TEXT, msg);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        displayMessage(msg);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}