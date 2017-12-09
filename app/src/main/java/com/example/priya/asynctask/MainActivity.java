package com.example.priya.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView content;
    EditText text;
    Button ok,delete;
    static String FILENAME = "test.txt";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIds();
        // creates file
       File extstorage = Environment.getExternalStorageDirectory();
       File path = new File(extstorage.getAbsolutePath() + "/myfolder",FILENAME);
        try {
            if (file.createNewFile()) {
                Toast.makeText(getApplicationContext(), "File Created",
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        //  Saving data to file
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = text.getText().toString();
                text.setText("");
                readFromFile rf = new readFromFile(file);
                rf.execute(value);
                Toast.makeText(getApplicationContext(), "Data Added",
                        Toast.LENGTH_LONG).show();
            }
        });

        // deletes the file
delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        file = new File(path, FILENAME);
        boolean isSuccess = file.delete();
        Toast.makeText(getApplicationContext(), "File Deleted",
                Toast.LENGTH_LONG).show();
    }
});
    }

    public void setIds() {
        content = (TextView) findViewById(R.id.response) ;
        text = (EditText) findViewById(R.id.myInputText);
        ok = (Button) findViewById(R.id.saveExternalStorage);
        delete = (Button) findViewById(R.id.removeExternalStorage);
    }

  // AsyncTask
   private class readFromFile extends AsyncTask<String, Integer, String> {

        File f;

        public readFromFile(File f) {
            super();
            this.f = f;
// TODO Auto-generated constructor stub
        }
 
       // Perform an operation on a background thread
        @Override
        protected String doInBackground(String... str) {
            String enter = "\n";
            FileWriter writer = null;
            try {
                writer = new FileWriter(f, true);
                writer.append(str[0].toString());
                writer.append(enter);
                writer.flush();

            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;

        }

        // Runs on the UI thread after doInBackground()
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name = "";
            StringBuilder sb = new StringBuilder();
            FileReader fr = null;

            try {
                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while ((name = br.readLine()) != null) {
                    sb.append(name);
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            content.setText(sb.toString());
        }
    }
}
