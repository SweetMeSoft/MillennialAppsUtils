package co.com.millennialapps.utils.tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by erick on 29/6/2017.
 */
public class DownloadImage extends AsyncTask<String, String, Bitmap> {

    private Context context;
    private ImageView imageView;
    private boolean saveImage;
    private String nameImage;
    private String dirImage;

    public DownloadImage(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
        this.saveImage = false;
    }

    public DownloadImage(Context context, ImageView imageView, String nameImage, String dirImage) {
        this.context = context;
        this.imageView = imageView;
        this.saveImage = true;
        this.nameImage = nameImage;
        this.dirImage = dirImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            if (saveImage) {
                ContextWrapper cw = new ContextWrapper(context);
                File directory = cw.getDir(dirImage, Context.MODE_PRIVATE);
                File f = new File(directory, nameImage);
                return BitmapFactory.decodeStream(new FileInputStream(f));
            }
            return downloadImage(strings[0]);
        } catch (FileNotFoundException e) {
            return downloadImage(strings[0]);
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            imageView.setImageBitmap(result);
        }
    }

    private Bitmap downloadImage(String url) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            if (saveImage) {
                ContextWrapper cw = new ContextWrapper(context);
                File directory = cw.getDir(dirImage, Context.MODE_PRIVATE);
                File mypath = new File(directory, nameImage);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(mypath));
            }
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}