package com.example.objectdetection;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.objectdetection.image.FaceDetectionActivity;
import com.example.objectdetection.image.FlowerIdentificationActivity;
import com.example.objectdetection.image.ImageClassificationActivity;
import com.example.objectdetection.image.ObjectDetectionActivity;
import com.example.objectdetection.textRecoganization.TextRecoganization;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGotoImageActivity(View view){
        Intent intent = new Intent(this, ImageClassificationActivity.class);
        startActivity(intent);
    }

    public void onGotoFlowerActivity(View view){
        Intent intent = new Intent(this, FlowerIdentificationActivity.class);
        startActivity(intent);
    }

    public void onGotoObjectDetection(View view){
        Intent intent = new Intent(this, ObjectDetectionActivity.class);
        startActivity(intent);
    }

    public void onGotoFaceDetection(View view){
        Intent intent = new Intent(this, FaceDetectionActivity.class);
        startActivity(intent);
    }

    public void onGotoAudioClassification(View view){
        Intent intent = new Intent(this, TextRecoganization.class);
        startActivity(intent);
    }

}