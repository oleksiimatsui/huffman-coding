package com.example.huffman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class DecodeResultFragment extends Fragment {
    private static final String ARG_PARAM1 = "CODE";
    private static final String ARG_PARAM2 = "ROOT";
    private HuffmanNode root;
    private String rootString;
    private String code;

    public DecodeResultFragment() {
        // Required empty public constructor
    }
    public static DecodeResultFragment newInstance(String param1, String param2) {
        DecodeResultFragment fragment = new DecodeResultFragment();
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
            code = getArguments().getString(ARG_PARAM1);
            String rootJson = getArguments().getString(ARG_PARAM2);
            Gson gson = new Gson();
            root = gson.fromJson(rootJson, HuffmanNode.class);
            rootString = rootJson;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_decode_result, container, false);
        TextView name = (TextView) rootView.findViewById(R.id.decoded_text);
        String decoded = Huffman.Decode(code, root);
        name.setText(decoded);
        return rootView;
    }
}