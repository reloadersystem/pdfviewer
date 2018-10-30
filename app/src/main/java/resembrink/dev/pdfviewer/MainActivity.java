package resembrink.dev.pdfviewer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfView=  findViewById(R.id.pdfView);
        //pdfView.fromAsset("cv_data.pdf").load(); //carga  pdf de  sdcard

        //new RetrievePDFStream().execute("http://ancestralauthor.com/download/sample.pdf"); //cargando desde una url

        //funcion  carga  con bytes

        new RetrievePDFBytes().execute("http://ancestralauthor.com/download/sample.pdf");
    }


    class RetrievePDFBytes extends AsyncTask<String,Void,byte[]>
    {
        @Override
        protected byte[] doInBackground(String... strings) {

            InputStream inputStream= null;

            try {

                URL url= new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                if(urlConnection.getResponseCode() == 200)
                {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            }
            catch (IOException e)
            {

                return null;
            }

            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {

            pdfView.fromBytes(bytes).load();

        }
    }

//    class RetrievePDFStream extends AsyncTask<String,Void,InputStream>
//    {
//        @Override
//        protected InputStream doInBackground(String... strings) {
//
//            InputStream inputStream= null;
//
//            try {
//
//                    URL url= new URL(strings[0]);
//                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
//
//                    if(urlConnection.getResponseCode() == 200)
//                    {
//                        inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                    }
//
//                }
//                catch (IOException e)
//                {
//
//                    return null;
//                }
//
//            return inputStream;
//        }
//
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//
//            pdfView.fromStream(inputStream).load();
//
//        }
//    }
}
