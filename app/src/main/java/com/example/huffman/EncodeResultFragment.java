package com.example.huffman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_encode_result, container, false);
        TextView name = (TextView) rootView.findViewById(R.id.encode_result);
        String encoded = Huffman.Encode(mParam1).encoded;
        name.setText(encoded);
        return rootView;
    }
}