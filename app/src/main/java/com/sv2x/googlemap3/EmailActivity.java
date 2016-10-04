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
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by bruno on 16. 9. 30.
 */

public class EmailActivity extends Activity {
    static final String AUTHORITIES_NAME = "com.sv2x.googlemap3.fileprovider";
    final String FOLDER_NAME = "files";
    final String FILE_NAME = "2016-09-304.txt";


    //Set up button and text fileds
    Button sendButton;
    EditText receiver_email;
    EditText mail_subject;
    EditText text_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);


        //Assign button and to the xml fields
        sendButton = (Button) findViewById(R.id.send_button);
        receiver_email = (EditText) findViewById(R.id.recepientEmail);
        mail_subject =(EditText) findViewById(R.id.Subject);
        text_message = (EditText) findViewById(R.id.Messge);



        //Set onClickListener for the send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendTo = receiver_email.getText().toString();
                String subject = mail_subject.getText().toString();
                String message = text_message.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("*/*");
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{sendTo});
                email.putExtra(Intent.EXTRA_SUBJECT,subject);
                email.putExtra(Intent.EXTRA_TEXT,message);
                //Deal with attachment
                File filePath = new File(getFilesDir(),FOLDER_NAME);
                File filesharing =  new File(filePath,FOLDER_NAME);
                Uri uri = FileProvider.getUriForFile(getApplicationContext(),AUTHORITIES_NAME,filesharing);
                email.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(email,"Select Email Client"));









                //Dealing with file attachment
                //File dir = getFilesDir();
                //Log.d("filePath",dir.toString());
                //File file = new File(dir.toString()+"/2016-09-300.txt");
                //Uri uri = Uri.fromFile(file);

                //String file_name = "2016-09-300.txt";
                //Toast.makeText(getApplicationContext(),uri.toString(), Toast.LENGTH_LONG).show();
                //email.putExtra(Intent.EXTRA_STREAM,uri);



                //To send the file, we must make a copy of it to external memory then send that copy









               /* try {

                    //Begin an email activity
                    startActivity(Intent.createChooser(email, "Select a client to send e-mail: "));
                    finish();
                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getApplicationContext(),"Unable to send message", Toast.LENGTH_LONG).show();
                }*/
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
