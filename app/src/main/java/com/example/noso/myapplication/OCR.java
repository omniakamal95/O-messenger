package com.example.noso.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class OCR extends AppCompatActivity {

    ImageView imageview;
    TextView txtResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        imageview = (ImageView) findViewById(R.id.image_view);
        txtResult = (TextView) findViewById(R.id.textview_result);

        txtResult.setText(OCR_Function(R.drawable.ocrtest4));
    }

    public String OCR_Function(Integer path) {
        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), path
        );
        imageview.setImageBitmap(bitmap);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.e("ERROR", "Detector Dependecies are not ready yet");
            return "";
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = items.valueAt(i);
                stringBuilder.append(item.getValue());
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }


    }
}
