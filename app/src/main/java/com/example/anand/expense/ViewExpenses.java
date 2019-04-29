package com.example.anand.expense;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ViewExpenses extends AppCompatActivity {

    DatabaseHelper myDb;
    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        myDb = new DatabaseHelper(this);

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }
        Expense expense_data[] = new Expense[ res.getCount()];
        int i=0;
        while (res.moveToNext()) {
            String id = res.getString(0);
            String tag = res.getString(1);
            String desc = res.getString(2);
            String amt = res.getString(3);
            expense_data[i++] = new Expense(id, tag, desc, amt);
        }
        ExpenseAdapter adapter = new ExpenseAdapter(this,
                R.layout.listview_item_row, expense_data);

        listView1 = (ListView) findViewById(R.id.listView1);

        View header = (View) getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView1.addHeaderView(header);
        listView1.setAdapter(adapter);
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
