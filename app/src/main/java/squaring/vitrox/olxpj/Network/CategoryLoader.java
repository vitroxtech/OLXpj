package squaring.vitrox.olxpj.Network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Model.FlickrResponse;
import squaring.vitrox.olxpj.Model.Photo;

public class CategoryLoader extends AsyncTaskLoader<List<Category>>
{
    List<Category> categories= new ArrayList<>();

    public CategoryLoader(Context context) {
        super(context);
    }

    @Override
    public List<Category> loadInBackground() {

        return categories;
    }


    @Override
    public void deliverResult(List<Category> data) {

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

    }
}