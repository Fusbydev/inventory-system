package com.example.practicecrude;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class transactionFragment extends Fragment {
    Button addTrans;
    Button addCredit;
    TextView day, week, month;
    DBHelper helper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        helper = new DBHelper(getContext());
        addTrans = view.findViewById(R.id.addTransaction);
        addCredit = view.findViewById(R.id.creditTrans);

        day = view.findViewById(R.id.today);
        week = view.findViewById(R.id.weekly);
        month = view.findViewById(R.id.monthly);


        day.setText(String.valueOf(helper.getDailyProfit()));
        week.setText(String.valueOf(helper.getWeeklyProfit()));
        month.setText(String.valueOf(helper.getMonthlyProfit()));
        addTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), transactionAct.class);
                startActivity(intent);
            }
        });

        addCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), addCreditLoan.class));
            }
        });
        // Display past transactions
        displayPastTransactions(view);

        return view;
    }

    private void displayPastTransactions(View view) {
        // Replace this with your actual data retrieval logic
        ArrayList<Transaction> transactions = getDataFromDatabase();

        // Access the TableLayout
        TableLayout transactionTable = view.findViewById(R.id.transactionTableLayout);

        // Create header row
        TableRow headerRow = createTableRow();
        addHeaderTextView(headerRow, "Transaction ID");
        addHeaderTextView(headerRow, "Product ID");
        addHeaderTextView(headerRow, "Product Name");
        addHeaderTextView(headerRow, "Quantity");
        transactionTable.addView(headerRow);

        // Create rows for each transaction and add them to the table
        for (Transaction transaction : transactions) {
            TableRow row = createTableRow();

            addDataTextView(row, String.valueOf(transaction.getTransID()));
            addDataTextView(row, String.valueOf(transaction.getProductId()));
            addDataTextView(row, transaction.getProductName());
            addDataTextView(row, String.valueOf(transaction.getQuantity()));

            transactionTable.addView(row);
        }
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(requireContext());
        row.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        row.setBackgroundResource(R.drawable.table_border);

        return row;
    }

    private void addHeaderTextView(TableRow row, String text) {
        TextView textView = createTextView(text, true);
        row.addView(textView);
    }

    private void addDataTextView(TableRow row, String text) {
        TextView textView = createTextView(text, false);
        row.addView(textView);
    }

    private TextView createTextView(String text, boolean isHeader) {
        TextView textView = new TextView(requireContext());
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextColor(isHeader ? Color.WHITE : Color.BLACK);
        textView.setTypeface(null, isHeader ? Typeface.BOLD : Typeface.NORMAL);

        textView.setBackgroundResource(R.drawable.table_cell_border);

        return textView;
    }

    private ArrayList<Transaction> getDataFromDatabase() {
        DBHelper dbHelper = new DBHelper(requireContext());
        ArrayList<Transaction> transactions = dbHelper.getAllTransactions();
        return transactions;
    }
}
