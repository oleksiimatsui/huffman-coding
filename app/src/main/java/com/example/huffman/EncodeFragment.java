package com.example.huffman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.huffman.databinding.FragmentEncodeBinding;

public class EncodeFragment extends Fragment {

    private FragmentEncodeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEncodeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_encode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tv = (EditText)(getView().findViewById(R.id.input_text));
                String text = tv.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("TEXT", text);
                NavHostFragment.findNavController(EncodeFragment.this)
                        .navigate(R.id.action_encode_fragment_to_encodeResultFragment, bundle);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}