package com.example.practicecrude;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button InventoryButton;
    Button creditButton;
    Button income;
    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        InventoryButton = view.findViewById(R.id.inventoryButton);
        InventoryButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), inventoryActivity.class);
            startActivity(intent);
        });

        creditButton = view.findViewById(R.id.TransactionButton);
        creditButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), creditAct.class);
            startActivity(intent);
        });

        income = view.findViewById(R.id.incometracker);
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FragmentManager fragmentManager = requireActivity().getSupportFragmentManager(); // For Fragment

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new transactionFragment());
                fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                fragmentTransaction.commit();
            }
        });
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
        ArrayList<Transaction> transactions = dbHelper.getAllTransactionsTop(3);
        return transactions;
    }
}