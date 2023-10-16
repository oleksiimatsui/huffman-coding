package com.example.huffman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class DecodeResultFragment extends Fragment {
    private static final String ARG_PARAM1 = "DATA";
    private String dataStr;
    private HuffmanEncodeData data;

    public DecodeResultFragment() {
        // Required empty public constructor
    }
    public static DecodeResultFragment newInstance(String param1) {
        DecodeResultFragment fragment = new DecodeResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataStr = getArguments().getString(ARG_PARAM1);
            Gson gson = new Gson();
            data = gson.fromJson(dataStr, HuffmanEncodeData.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_decode_result, container, false);
        TextView name = (TextView) rootView.findViewById(R.id.decoded_text);
        String decoded = Huffman.Decode(data.code, data.root);
        name.setText(decoded);
        return rootView;
    }
}