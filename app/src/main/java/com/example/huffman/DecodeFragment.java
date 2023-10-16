package com.example.huffman;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.huffman.databinding.FragmentDecodeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DecodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DecodeFragment extends Fragment {
    public DecodeFragment() {
        // Required empty public constructor
    }

    private FragmentDecodeBinding binding;
    public static DecodeFragment newInstance() {
        DecodeFragment fragment = new DecodeFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decode, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog dialog = new Dialog(view.getContext());

        ArrayList<String> list = new ArrayList<>();

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            list.add(files[i].getName());
        }
        android.content.Context context = this.getContext();


        dialog.setContentView(R.layout.open_file_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        View open = view.findViewById(R.id.open_file_btn);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayAdapter adapter = new ArrayAdapter(context, R.layout.text_view, list);
                ListView listView = dialog.findViewById(R.id.list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView <?> parentAdapter, View view, int position,
                                            long id) {
                        Object textView = parentAdapter.getItemAtPosition(position);
                        String file = textView.toString();
                        String dataStr = decodeFile(view, path, file);
                        Bundle bundle = new Bundle();
                        bundle.putString("DATA", dataStr);
                        NavHostFragment.findNavController(DecodeFragment.this)
                                .navigate(R.id.action_decode_fragment_to_decodeResultFragment, bundle);
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });
    }

    public String decodeFile(View view, String path, String name) {
        String myData = "";
        try {
            FileInputStream fis = new FileInputStream(path + "/" + name);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Snackbar.make(view, myData, Snackbar.LENGTH_SHORT).show();
        return myData;
    }

}