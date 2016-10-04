package com.sv2x.googlemap3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by bruno on 16. 9. 30.
 */

public class EmailActivity extends Activity {
    static final String AUTHORITIES_NAME = "com.sv2x.googlemap3.fileprovider";
    final String FOLDER_NAME = "/";
    //Set up button and text fileds
    Button sendButton;
    EditText receiver_email;
    EditText mail_subject;
    EditText text_message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        Intent intent = getIntent();
        final String FILE_NAME = intent.getStringExtra("FILENAME");

        //Assign button and to the xml fields
        sendButton = (Button) findViewById(R.id.send_button);
        receiver_email = (EditText) findViewById(R.id.recepientEmail);
        mail_subject =(EditText) findViewById(R.id.Subject);
        text_message = (EditText) findViewById(R.id.Messge);



        //Set onClickListener for the send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(getApplicationContext().getFilesDir(),FILE_NAME);
                //Log.d("FilesFolder", file.toString());

                String sendTo = receiver_email.getText().toString();
                String subject = mail_subject.getText().toString();
                String message = text_message.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("*/*");
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{sendTo});
                email.putExtra(Intent.EXTRA_SUBJECT,subject);
                email.putExtra(Intent.EXTRA_TEXT,message);
                email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                File imagePath = new File(getFilesDir(), FOLDER_NAME);
                File filesharing = new File(imagePath,FILE_NAME);
                Uri uri = FileProvider.getUriForFile(getApplicationContext(),AUTHORITIES_NAME,filesharing);
                email.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(email,"Select Email Client"));
            }
        });
    }

    private static boolean copyFile(String oldPath, String newPath) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if(sd.canWrite()){
                int end = oldPath.toString().lastIndexOf("/");
                String str1 = oldPath.toString().substring(0, end);
                String str2 = oldPath.toString().substring(end+1, oldPath.length());
                File source = new File(str1, str2);
                File destination = new File(newPath, str2);
                if(source.exists()){
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }

            }
            return true;
        }   catch (Exception e){
            return false;
        }
    }
}
