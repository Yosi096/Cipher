package Kripto.tugas2.cipher.ui.playfair;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import Kripto.tugas2.cipher.PlayfairCipher;
import Kripto.tugas2.cipher.R;

public class PlayfairFragment extends Fragment {
    private Button btn_resetPlay, btn_en, btn_dec;
    private EditText et_plainPlay, et_keyPlay;
    TextView out;
    String in, output, key;
    SharedPreferences prefs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_playfair, container, false);

        out = (TextView) root.findViewById(R.id.txt_output);
        btn_resetPlay = (Button) root.findViewById(R.id.btn_resetPlay);
        btn_en = (Button) root.findViewById(R.id.btn_en);
        btn_dec = (Button) root.findViewById(R.id.btn_dec);
        et_plainPlay = (EditText) root.findViewById(R.id.et_plainPlay);
        et_keyPlay = (EditText) root.findViewById(R.id.et_keyPlay);

        btn_resetPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_plainPlay.setText("");
                et_keyPlay.setText("");
                out.setText("");
            }
        });

        btn_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in=et_plainPlay.getText().toString();
                key=et_keyPlay.getText().toString();
                output = PlayfairCipher.encrypt(in,key);
                out.setText(output);
            }
        });

        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in=et_plainPlay.getText().toString();
                key=et_keyPlay.getText().toString();
                output = PlayfairCipher.decrypt(in,key);
                out.setText(output);
            }
        });
        return root;
    }
}