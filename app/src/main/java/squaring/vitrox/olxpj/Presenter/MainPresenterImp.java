package squaring.vitrox.olxpj.Presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import squaring.vitrox.olxpj.Adapters.CategoryAdapter;
import squaring.vitrox.olxpj.Adapters.OnItemClickListener;
import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Helper.RandomHelper;
import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Model.FlickrResponse;
import squaring.vitrox.olxpj.Model.Photo;
import squaring.vitrox.olxpj.Network.ApiClient;
import squaring.vitrox.olxpj.Network.ApiInterface;
import squaring.vitrox.olxpj.db.DatabaseHandler;

public class MainPresenterImp implements MainPresenter {

    private View view;
    private Context mContext;
    private List<Category> categories;

    public MainPresenterImp(View v, Context context) {
        this.mContext = context;
        this.view = v;
    }

    /*
    Get the categories from flickr in async way then for each response i add the category getted on view this for make the view
    more responsive and not wait to all  categories
    */
    @Override
    public void onload() {
        RandomHelper rd = new RandomHelper();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        categories = getDbCategories();
        for (final Category st : categories) {
            Call<FlickrResponse> call = apiService.getidPhotoCategory(st.getCategoryname(), rd.getRandomIntInRange(0, 10));
            call.enqueue(new Callback<FlickrResponse>() {
                @Override
                public void onResponse(Response<FlickrResponse> response, Retrofit retrofit) {
                    Photo myphoto = response.body().getPhotos().getPhoto().get(0);
                    st.setLink(Config.getUrl(myphoto));
                    view.refresh(st, getMostViewedCategories());
                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Log.e("SHIT: ", t.toString());
                }
            });
        }
    }

    @Override
    public void onItemClick(Category item) {
        DatabaseHandler dbh = DatabaseHandler.getdatabaseHandler(mContext);
        dbh.updateCategory(item);
        view.clickSaved(item.getCategoryname());

    }

    private List<Category> getDbCategories() {
        DatabaseHandler dbh = DatabaseHandler.getdatabaseHandler(mContext);
        return dbh.getAllCategories();
    }

    /*
    Obtains  the two most viewed categories in order to setup the proper span on activity
    */
    private List<Category> getMostViewedCategories() {
        int max = 0;
        int secondMax = 0;
        Category Cmax = new Category();
        Category CSmax = new Category();
        List<Category> catgs = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            int toeval = Integer.parseInt(categories.get(i).getClicks());
            if (toeval > max) {
                max = toeval;
                Cmax = categories.get(i);
            } else if (toeval > secondMax) {
                secondMax = toeval;
                CSmax = categories.get(i);
            }
        }
        if (max != 0) {
            catgs.add(Cmax);
        }
        if (secondMax != 0) {
            catgs.add(CSmax);
        }
        return catgs;
    }


}
