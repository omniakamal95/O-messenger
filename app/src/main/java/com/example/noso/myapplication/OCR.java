package com.example.noso.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class OCR extends AppCompatActivity {

    ImageView imageview;
    Button btnprocess;
    TextView txtResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        imageview = (ImageView) findViewById(R.id.image_view);
        btnprocess=(Button)findViewById(R.id.button_process);
        txtResult=(TextView)findViewById(R.id.textview_result);
//asfasfa
        final Bitmap bitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.ocrtest
                );
        imageview.setImageBitmap(bitmap);
        btnprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextRecognizer textRecognizer=new TextRecognizer.Builder(getApplicationContext()).build();
                if(!textRecognizer.isOperational()) {
                    Log.e("ERROR", "Detector Dependecies are not ready yet");
                }
                else
                {
                    Frame frame=new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items=textRecognizer.detect(frame);
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0;i<items.size();i++)
                    {
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");

                    }
                    txtResult.setText(stringBuilder.toString());
                    Toast.makeText(OCR.this, stringBuilder.toString(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
