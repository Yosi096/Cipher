package Kripto.tugas2.cipher.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import Kripto.tugas2.cipher.R;

public class VigenereFragment extends Fragment {
    private Switch sw;
    TextView enDec;
    private EditText in_plain, in_key;
    private Button btn_run, btn_reset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vigenere, container, false);
        initView(view);

        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                encryptdanDecrypt(view);
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in_plain.setText("");
                in_key.setText("");
                enDec.setText("");
            }
        });
        return view;
    }

    private void initView(View view) {
        sw = view.findViewById(R.id.sw);
        enDec = view.findViewById(R.id.enDec);
        in_plain = view.findViewById(R.id.in_plain);
        in_key = view.findViewById(R.id.in_key);
        btn_run = view.findViewById(R.id.btn_run);
        btn_reset = view.findViewById(R.id.btn_reset);
    }

    public void encryptdanDecrypt(View view) {
        String string;
        if (view.getId() == R.id.btn_run) {
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            String plainText = this.in_plain.getText().toString();
            String key = this.in_key.getText().toString();

            if (!checkEmptyt(plainText, key)) {
                if (sw.isChecked()) {
                    string = this.decrypt(plainText, key);
                } else {
                    string = this.encrypt(plainText, key);
                }
                this.enDec.setText(string.toString());
            }
        }

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sw.isChecked()) {
                    String result = enDec.getText().toString();
                    in_plain.setText(result);
                    in_key.setText("");
                    sw.setText("Decrypt");
                } else {
                    String result = enDec.getText().toString();
                    in_plain.setText(result);
                    in_key.setText("");
                    sw.setText("Encrypt");
                }
            }
        });
    }

    private boolean checkEmptyt(String text, String keyphrase) {
        boolean isError = false;
        if (!text.matches(".*[a-zA-Z]+.*")) {
            this.in_plain.setError("Plain text harus diisi");
            isError = true;
        }

        if (null == keyphrase || keyphrase.isEmpty()) {
            this.in_key.setError("Key haru diisi");
            isError = true;
        }

        boolean valid = this.checkKeyValid(keyphrase);
        if (!valid) {
            this.in_key.setError("Key harus alfabet");
            isError = true;
        }
        return isError;
    }

    private boolean checkKeyValid(String keyphrase) {
        boolean valid = true;
        for (int z = 0; z < keyphrase.length(); ++z) {
            char c = keyphrase.charAt(z);
            if (!Character.isAlphabetic(c)) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    private String decrypt(String text, String keyphrase) {
        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {
            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);
            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char) ((upper - keyphrase.charAt(j) + 26) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char) ((upper - keyphrase.charAt(j) + 26) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    }

    private String encrypt(String text, String keyphrase) {
        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char) ((upper + keyphrase.charAt(j) - 26) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char)
                            ((upper + keyphrase.charAt(j) - 26) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    }
}