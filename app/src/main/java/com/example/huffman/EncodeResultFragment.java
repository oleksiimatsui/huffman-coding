package com.example.huffman;

import static android.view.View.generateViewId;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;


public class EncodeResultFragment extends Fragment {

    private static final String ARG_PARAM1 = "TEXT";
    private static final String ARG_PARAM2 = "ENCODEDATA";

    private String text;

    private HuffmanEncodeData data;
    public EncodeResultFragment() {
        // Required empty public constructor
    }

    public static EncodeResultFragment newInstance(String text, HuffmanEncodeData data) {
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
            text = getArguments().getString(ARG_PARAM1);
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

        HuffmanEncodeResult huffmanResult = Huffman.Encode(text);
        String encoded = huffmanResult.encoded;
        name.setText(encoded);
        String code = encoded;
        HuffmanNode root = huffmanResult.root;
        data = new HuffmanEncodeData(code, root);
        TableLayout table = (TableLayout) (rootView.findViewById(R.id.encode_table));
        huffmanResult.table.forEach((key, value) -> {
            char c = key;
            int freq = value.freq;
            String codee = value.code;
            TableRow tr_head = new TableRow(this.getContext());
            tr_head.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tr_head.setWeightSum(3);

            tr_head.addView(label(String.valueOf(c)));// add the column to the table row here
            tr_head.addView(label(String.valueOf(codee)));
            tr_head.addView(label(String.valueOf(freq)));

            table.addView(tr_head);
        });

        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.decode_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String rootString = gson.toJson(data);
                bundle.putString("EncodeData", rootString);
                NavHostFragment.findNavController(EncodeResultFragment.this)
                        .navigate(R.id.action_encodeResultFragment_to_decodeResultFragment, bundle);
            }
        });

        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.name_dialog);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



                View save = dialog.findViewById(R.id.save_file_btn);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Gson gson = new Gson();
                        String dataString = gson.toJson(data);
                        TextInputLayout textInputLayout = dialog.findViewById(R.id.filename_input_layout);
                        String text = String.valueOf(textInputLayout.getEditText().getText());
                        dialog.dismiss();
                        writeToFile(view, text, dataString);
                    }
                });
                dialog.show();
            }
        });
    }

    public void writeToFile(View view, String name, String text) {

        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            //If it isn't mounted - we can't write into it.
            return;
        }
        //Create a new file that points to the root directory, with the given name:
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), name + ".txt");
        //This point and below is responsible for the write operation
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.close();
            Snackbar.make(view, "File saved!", Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(view, e.getMessage() + "" + name, Snackbar.LENGTH_SHORT).show();
        }
    }
}