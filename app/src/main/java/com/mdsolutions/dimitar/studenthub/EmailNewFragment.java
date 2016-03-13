package com.mdsolutions.dimitar.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailNewFragment extends Fragment {

Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email_new, container, false);
       final EditText subjectEmail = (EditText)view.findViewById(R.id.editTextTitle);
       final EditText contentEmail = (EditText)view.findViewById(R.id.editTextContent);
       final Button button = (Button) view.findViewById(R.id.buttonSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"studenthubcoolschool@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, subjectEmail.getText());
                i.putExtra(Intent.EXTRA_TEXT, contentEmail.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(EmailNewFragment.this.getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
