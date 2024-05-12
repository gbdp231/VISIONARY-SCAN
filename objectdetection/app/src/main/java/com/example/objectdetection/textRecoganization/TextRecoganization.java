package com.example.objectdetection.textRecoganization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.objectdetection.MainActivity;
import com.example.objectdetection.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class TextRecoganization extends AppCompatActivity {
    Button camerabtn,copybtn,clearbtn;
    Uri imageUri;
    EditText recgText;
    TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recoganization);

        camerabtn=findViewById(R.id.camerabtn);
        copybtn=findViewById(R.id.copybtn);
        clearbtn=findViewById(R.id.clearbtn);
        recgText = findViewById(R.id.recgText);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(TextRecoganization.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = recgText.getText().toString();

                if (text.isEmpty()){
                    Toast.makeText(TextRecoganization.this, "Text is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(TextRecoganization.this.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Data",recgText.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(TextRecoganization.this, "Text copy in Clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = recgText.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(TextRecoganization.this, "Text is empty", Toast.LENGTH_SHORT).show();
                }else {
                    recgText.setText("");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode== Activity.RESULT_OK){
            if (data!=null){
                imageUri = data.getData();
                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();

                recoganizetionText();
            }

        }
        else{
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void recoganizetionText() {
        if (imageUri!=null){
            try {
                InputImage inputImage=InputImage.fromFilePath(TextRecoganization.this,imageUri);


                Task<Text> result = textRecognizer.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text text) {
                               String recognizeText = text.getText();
                               recgText.setText(recognizeText);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TextRecoganization.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}