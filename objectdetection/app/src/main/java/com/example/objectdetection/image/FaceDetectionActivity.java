package com.example.objectdetection.image;

import android.graphics.Bitmap;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.objectdetection.helper.BoxWithLabel;
import com.example.objectdetection.helper.ImageHelperActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.ArrayList;
import java.util.List;

public class FaceDetectionActivity  extends ImageHelperActivity {

    private FaceDetector faceDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .enableTracking()
                        .build();

        faceDetector = FaceDetection.getClient(highAccuracyOpts);
    }

    @Override
    protected void runclassification(Bitmap bitmap) {

        InputImage inputImage = InputImage.fromBitmap(bitmap,0);

        faceDetector.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                    @Override
                    public void onSuccess(List<Face> faces) {
                        if (faces.isEmpty()){
                            getTextViewOutput().setText("No face detected");
                        }else {
                            List<BoxWithLabel> boxes = new ArrayList<>();
                            for (Face face  : faces) {
                                BoxWithLabel boxWithLabel = new BoxWithLabel(
                                        face.getBoundingBox(),
                                        face.getTrackingId() + ""
                                );
                                boxes.add(boxWithLabel);
                            }
                            drawDetectionResult(boxes,bitmap);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
