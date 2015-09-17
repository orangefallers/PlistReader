package com.ricky.plistreader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by ricky on 2015/8/17.
 */
public class QRscanActivity extends Activity {


    private Activity mAct;
    private Button scanBtn;
    private TextView scanResultView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscan_layout);
        initView();

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(mAct);
                scanIntegrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult!=null){
            String scanContent=scanningResult.getContents();
            String scanFormat=scanningResult.getFormatName();
            scanResultView.setText(scanContent+ "  " +scanFormat);

            if (scanFormat.equals("QR_CODE")){
                Uri uri = Uri.parse(scanContent);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }

        }else{
            Toast.makeText(getApplicationContext(), "nothing", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(){
        mAct = this;
        scanBtn = (Button) findViewById(R.id.scanBtn);
        scanResultView = (TextView) findViewById(R.id.scanResult);

    }
}
