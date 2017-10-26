package com.example.admin.justjava; /**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 *
 */



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.admin.justjava.R.id.editTextName;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {



    boolean hasWhippedCream = false;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    // получение значения к-ва чащек кофе
    public void getQuantity(){
        TextView textView = (TextView) findViewById(R.id.quantity_text_view);
        quantity = Integer.valueOf(textView.getText().toString());

    }

    public void submitOrder(View view) {
        // получение значения к-ва чащек кофе
        getQuantity();

        EditText editTextName = (EditText) findViewById(R.id.editTextName);

       // displayMessage( createOrderSummary( editTextName.getText().toString(), quantity) );
        // Log.v("MainActivity.java", "The price is " + hasWhippedCream);

        // создание интента для запуска Gmail.com
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
       // intent.putExtra(Intent.EXTRA_EMAIL, "mail@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + editTextName.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary( editTextName.getText().toString(), quantity));

        if (intent.resolveActivity(getPackageManager()) != null ){
            startActivity(Intent.createChooser(intent, "Send Email"));
        }

    }

    public int calculatePrice(){
        CheckBox boxCream = (CheckBox) findViewById(R.id.checkBoxWhippedCream);
        CheckBox boxChocolate = (CheckBox) findViewById(R.id.checkboxChocolate);
        // получение значения к-ва чащек кофе
        getQuantity();


        if ( statusCheckBox(boxCream) == true && statusCheckBox(boxChocolate) ){
            quantity = (5 + 1 + 2) * quantity;
        }
        else if ( statusCheckBox(boxCream) ){
            quantity = (5 + 1) * quantity;
        }
        else if (statusCheckBox(boxChocolate)){
            quantity = (5 + 2) * quantity;
        }
        else {
            quantity *= 5;
        }

        return quantity;
    }

    // получние boolean - значения с CheckBox
    public boolean statusCheckBox(CheckBox box){

        if (box.isChecked()) {
            hasWhippedCream = true;
        }else {
            hasWhippedCream = false;
        }

        return hasWhippedCream;

    }



    public String createOrderSummary(String name, int quantity){

        CheckBox boxCream = (CheckBox) findViewById(R.id.checkBoxWhippedCream);
        CheckBox boxChocolate = (CheckBox) findViewById(R.id.checkboxChocolate);


        String line = getString(R.string.order_summary_name, name) + "\n";
               line += getString(R.string.whipped_cream) + " " + statusCheckBox(boxCream)+ "\n";
               line += getString(R.string.chocolate) + " " + statusCheckBox(boxChocolate) + "\n";
               line += getString(R.string.quantity) + " " + quantity + "\n";
               line += getString(R.string.total) + " " + calculatePrice();
               line += "\n" + getString(R.string.thank_you);

        return line;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberMy) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberMy);
    }


    public void increment(View view){
        getQuantity();
        if (quantity < 100){
            quantity++;
        }else{
            quantity = 100;
            displayToast("Больше 100-а чашек кофе заказать нельзя!");
        }

        displayQuantity(quantity);
    }

    public void decrement(View view){
        getQuantity();



        if (quantity > 1){
            quantity--;
        } // если quantity < 0 тогда обнуляем переменную
        else {
            quantity = 1;
            displayToast("Меньше одной чашки кофе заказать нельзя!");
        }
        displayQuantity(quantity);
    }



    // вывод свплывающего сообщения Toast
    private void displayToast(String line){
        Toast toast = Toast.makeText(getApplicationContext(), " " + line, Toast.LENGTH_SHORT);
        toast.show();
    }

}