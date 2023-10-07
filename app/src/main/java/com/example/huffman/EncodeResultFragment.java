package com.example.huffman;

import static android.view.View.generateViewId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncodeResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncodeResultFragment extends Fragment {

    private static final String ARG_PARAM1 = "TEXT";

    private String mParam1;
    public EncodeResultFragment() {
        // Required empty public constructor
    }

    public static EncodeResultFragment newInstance(String text) {
        EncodeResultFragment fragment = new EncodeResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    private TextView label(String text){
        TextView label = new TextView(this.getContext());    // part3
        label.setId( generateViewId ());
        label.setText(text);
        label.setBackgroundResource(R.drawable.border);
        label.setPadding(10, 10, 10, 10);
        LinearLayout.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,5f);
        params.weight = 1;
        label.setLayoutParams(params);
        return label;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_encode_result, container, false);
        TextView name = (TextView) rootView.findViewById(R.id.encode_result);

        HuffmanEncodeResult huffmanResult = Huffman.Encode(mParam1);
        String encoded = huffmanResult.encoded;
        name.setText(encoded);

        TableLayout table = (TableLayout) (rootView.findViewById(R.id.encode_table));
        huffmanResult.table.forEach((key, value) -> {
            char c = key;
            int freq = value.freq;
            String code = value.code;
            TableRow tr_head = new TableRow(this.getContext());
            tr_head.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tr_head.setWeightSum(3);

            tr_head.addView(label(String.valueOf(c)));// add the column to the table row here
            tr_head.addView(label(String.valueOf(code)));
            tr_head.addView(label(String.valueOf(freq)));

            table.addView(tr_head);
        });

        return rootView;
    }
}