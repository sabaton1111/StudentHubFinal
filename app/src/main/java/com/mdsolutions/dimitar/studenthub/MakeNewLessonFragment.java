package com.mdsolutions.dimitar.studenthub;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class MakeNewLessonFragment extends Fragment
{
    private ScrollView mScrollView;
    private RelativeLayout mFormView;
    private EditText editTextVideoLink;
    private EditText editTextHomework;
    private EditText editTextLectors;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextResources;
    private ImageView imageViewCourseImage;
    private Spinner spinnerSubjects;
    private Button buttonLoadImage;
    private FloatingActionButton buttonMakeCourse;
    private ParseFile courseImage;
    HashMap<String, Object> parseRequestHashMap = new HashMap<>();
    HashMap<String, String> lectorsByNameHashMap = new HashMap<>();

    private static int RESULT_LOAD_IMAGE = 1;
    Uri myPicture = null;
    String picturePath;
    boolean isLectorExisting = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_new_lesson, container, false);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollViewNewLesson);
        mFormView = (RelativeLayout) view.findViewById(R.id.form);
        buttonLoadImage = (Button) view.findViewById(R.id.selectPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
        editTextVideoLink = (EditText) view.findViewById(R.id.editTextVideoLink);
        editTextHomework = (EditText) view.findViewById(R.id.editTextHomework);
        editTextLectors = (EditText) view.findViewById(R.id.editTextLectors);
        editTextTitle = (EditText) view.findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) view.findViewById(R.id.editTextContent);
        editTextResources = (EditText) view.findViewById(R.id.editTextResources);
        imageViewCourseImage = (ImageView) view.findViewById(R.id.imageViewNewCourse);
        spinnerSubjects = (Spinner) view.findViewById(R.id.spinnerSubjects);
        buttonMakeCourse = (FloatingActionButton) view.findViewById(R.id.fab_save_lesson);

        buttonMakeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNewCourse();
            }
        });

        editTextLectors.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    CheckIfUserExists(editTextLectors.getText().toString().trim());
                } else {
                    editTextLectors.setBackgroundColor(Color.rgb(0xf5, 0xf5, 0xf5));
                    isLectorExisting = false;
                }
            }
        });

        return view;

    }

    public void makeNewCourse() {
        String videoLink = editTextVideoLink.getText().toString();
        String homework = editTextHomework.getText().toString();
        String lectors = editTextLectors.getText().toString();
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String resources = editTextResources.getText().toString();
        // TODO: Add objects to Parse HashMap
        byte[] image = new byte[0];

        if(picturePath == null)
        {
            // TODO: CREATE TOAST
            return;
        }
        try {
            image = readInFile(this.picturePath);
        }
        catch (Exception e) {
            image = null;
            e.printStackTrace();
        }

        courseImage = new ParseFile(image);
        courseImage.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    MakeCourse();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void MakeCourse()
    {
        boolean loaded = loadRequestHashMap();
        if(loaded)
        {
            ParseCloud.callFunctionInBackground("MakeCourse", parseRequestHashMap, new FunctionCallback<Object>() {
                @Override
                public void done(Object object, ParseException e) {
                    if (e == null) {
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "Username not valid", Toast.LENGTH_SHORT);
        }
    }

    private boolean loadRequestHashMap()
    {
        if(!isLectorExisting)
        {
            return false;
        }
        String videoLink = editTextVideoLink.getText().toString();
        String homework = editTextHomework.getText().toString();
        String lectors = editTextLectors.getText().toString();
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String resources = editTextResources.getText().toString();

        parseRequestHashMap.put("title", title);
        parseRequestHashMap.put("homework", homework);
        parseRequestHashMap.put("description", description);
        parseRequestHashMap.put("resourcesLink", resources);
        parseRequestHashMap.put("videoLink", videoLink);
        ArrayList<String> lectorsArray = new ArrayList<>();
        lectorsArray.add(lectorsByNameHashMap.get(lectors));

        parseRequestHashMap.put("lectorsArray", lectorsArray);
        parseRequestHashMap.put("picture", courseImage);
        parseRequestHashMap.put("subjectId", "Q44AJMZQY4");
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) mFormView.findViewById(R.id.imageViewNewCourse);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            this.picturePath = picturePath;
        }
    }

    private byte[] readInFile(String path) throws IOException {
        byte[] data = null;
        File file = new File(path);
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        inputStream.close();
        return buffer.toByteArray();
    }

    private void CheckIfUserExists(final String username)
    {
        final HashMap<String, Object> requestHashMap = new HashMap<>();
        if(lectorsByNameHashMap.containsKey(username)) {
            editTextLectors.setBackgroundColor(Color.BLUE);
            isLectorExisting = true;
        }
        else {
            requestHashMap.put("username", username);
            ParseCloud.callFunctionInBackground("CheckUser", requestHashMap, new FunctionCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if (e == null) {
                        if (parseObject != null) {
                            editTextLectors.setBackgroundColor(Color.BLUE);
                            isLectorExisting = true;
                            lectorsByNameHashMap.put(username, parseObject.getObjectId());
                        } else {
                            editTextLectors.setBackgroundColor(Color.RED);
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}