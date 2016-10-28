package squaring.vitrox.olxpj.Presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Helper.RandomHelper;
import squaring.vitrox.olxpj.Helper.SharedPrefHelper;
import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Model.FlickrResponse;
import squaring.vitrox.olxpj.Model.Photo;
import squaring.vitrox.olxpj.Network.ApiClient;
import squaring.vitrox.olxpj.Network.ApiInterface;
import squaring.vitrox.olxpj.db.DatabaseHandler;

public class FirstActivityPresenterImp implements FirstActivityPresenter {

    private FirstActivityPresenter.view view;
    private Context mContext;
    private Category currentMVCategory;
    public FirstActivityPresenterImp(FirstActivityPresenter.view v, Context context) {
        this.mContext = context;
        this.view = v;
    }

    /* get if first time from shared preferences */
    @Override
    public void checkIfFirstTime() {
        SharedPrefHelper prefHelper = new SharedPrefHelper(mContext);
        if (prefHelper.isFirstTime()) {
            fillFirstTimeDb();
        }
        view.isfirstTime(prefHelper.isFirstTime());
    }

    /* Get a random image for the category wich is the most clicked */
    @Override
    public void getMostClickedCat() {
        RandomHelper rh = new RandomHelper();
        DatabaseHandler DBH = DatabaseHandler.getdatabaseHandler(mContext);
        currentMVCategory = DBH.getMostViewedCategory();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FlickrResponse> call = apiService.getidPhotoCategory(currentMVCategory.getCategoryname(), rh.getRandomIntInRange(1, 10));
        call.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Response<FlickrResponse> response, Retrofit retrofit) {
                Photo my_photo = response.body().getPhotos().getPhoto().get(0);
                currentMVCategory.setLink(Config.getUrl(my_photo));
                view.mostViewedCategory(currentMVCategory);
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.e(" ", t.toString());
            }
        });
    }

    @Override
    public void onImageClicked() {
        DatabaseHandler dbh = DatabaseHandler.getdatabaseHandler(mContext);
        dbh.updateCategory(currentMVCategory);
        view.goToProducts(currentMVCategory.getCategoryname());
    }

    /* fill the db with all the categories for first instance*/
    private void fillFirstTimeDb() {
        DatabaseHandler DBH = DatabaseHandler.getdatabaseHandler(mContext);
        for (String cat : Config.CATEGORIES) {
            DBH.addCategory(cat);
        }
    }


}
