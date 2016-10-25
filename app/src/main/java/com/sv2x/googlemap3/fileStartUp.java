package com.sv2x.googlemap3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bruno on 16. 10. 25.
 */

public class fileStartUp extends MainActivity {
    Context contex;
    Uri data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  contex = this.getApplicationContext();
        //Get file information
        data = getIntent().getData();
        if(data != null){
            getIntent().setData(null);
            try{
                obtainData(data);
            }catch (Exception e){
                //Give some information of unavailable data here
                finish();
                return;
            }
        }
*/


        setContentView(R.layout.file_usage);

    }

    private void obtainData(Uri data) {
        final String  scheme = data.getScheme();
        if(ContentResolver.SCHEME_CONTENT.equals(scheme)){
            try{
                ContentResolver cr = contex.getContentResolver();
                InputStream is = cr.openInputStream(data);
                File file = new File(data.getPath());
                if(is == null)
                    return;

                StringBuffer buf = new StringBuffer();
                BufferedReader  reader =  new BufferedReader(new InputStreamReader(is));
                String str;
                if(is != null){
                    while ((str = reader.readLine()) != null){
                        buf.append(str + "\n");
                    }
                }
                is.close();
                Log.d("Date Read from file ", buf.toString());
                Toast toast = Toast.makeText(contex,file.getAbsolutePath().toString(),Toast.LENGTH_LONG);
                toast.show();
            }catch (Exception e){

            }
        }
    }

    public void ShowFileData(View view) {

    }

    public void SaveFile(View view) {
    }
}
