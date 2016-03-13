package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    public static final String TAG = "Test";
    String name;
    String firstName;
    String middleName;
    String lastName;
    String email;
    String organization;
    boolean isStudent;
    private ScrollView mScrollView;
    private RelativeLayout layout1;
    private RelativeLayout mFormView;

    private static int RESULT_LOAD_IMAGE = 1;
    Uri myPicture = null;
    Button buttonLoadImage;


    private static int sId = 0;

    private static int id() {
        return sId++;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mScrollView == null) {
            // normally inflate the view hierarchy


            layout1 = (RelativeLayout) inflater.inflate(R.layout.fragment_profile, container, false);
            mFormView = (RelativeLayout) layout1.findViewById(R.id.form1);


            //Getting user information and display it in profile section
            ParseUser.getCurrentUser();
            ParseUser currentUser = ParseUser.getCurrentUser();
            name = currentUser.getUsername();
            TextView nickname = (TextView) layout1.findViewById(R.id.textViewNickname);
            nickname.setText(name);

            firstName = currentUser.getString("firstName");
            TextView firstNameText = (TextView) layout1.findViewById(R.id.textViewFirstNameParse);
            firstNameText.setText(firstName);

            middleName = currentUser.getString("middleName");
            TextView middleNameText = (TextView) layout1.findViewById(R.id.textViewMiddleNameParse);
            middleNameText.setText(middleName);

            lastName = currentUser.getString("lastName");
            TextView lastNameText = (TextView) layout1.findViewById(R.id.textViewLastNameParse);
            lastNameText.setText(lastName);

            email = currentUser.getEmail();
            TextView emailText = (TextView) layout1.findViewById(R.id.textViewEmailParse);
            emailText.setText(email);

            organization = currentUser.getString("organization");
            TextView organizationText = (TextView) layout1.findViewById(R.id.textViewOrganizationParse);
            organizationText.setText(organization);

            isStudent = currentUser.getBoolean("isStudent");
            TextView isStudentText = (TextView) layout1.findViewById(R.id.textViewIsStudentParse);
            if(isStudent==true)
            {
                isStudentText.setText("Yes");
            }
            else {isStudentText.setText("No");}


            //Setting up the button which loads picture from gallery
            buttonLoadImage = (Button) layout1.findViewById(R.id.buttonProfilePicture);

            buttonLoadImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });


        } else {

            ViewGroup parent = (ViewGroup) layout1.getParent();
            parent.removeView(layout1);
        }
        return layout1;
    }


    //Displaying the picture in image view

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) mFormView.findViewById(R.id.imageViewProfilePicture);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}