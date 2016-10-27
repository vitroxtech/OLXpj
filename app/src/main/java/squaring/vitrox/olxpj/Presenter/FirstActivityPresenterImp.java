package squaring.vitrox.olxpj.Presenter;

import android.content.Context;
import android.util.Log;

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

    public FirstActivityPresenterImp(FirstActivityPresenter.view v, Context context) {
        this.mContext = context;
        this.view = v;
    }

    @Override
    public void checkIfFirstTime() {
        SharedPrefHelper prefHelper = new SharedPrefHelper(mContext);
        if (prefHelper.isFirstTime()) {
            fillFirstTimeDb();
        }
        view.isfirstTime(prefHelper.isFirstTime());
    }

    @Override
    public void getMostClickedCat() {
        RandomHelper rh = new RandomHelper();
        DatabaseHandler DBH = DatabaseHandler.getdatabaseHandler(mContext);
        final Category cat = DBH.getMostViewedCategory();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FlickrResponse> call = apiService.getidPhotoCategory(cat.getCategoryname(), rh.getRandomIntInRange(1, 10));
        call.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Response<FlickrResponse> response, Retrofit retrofit) {
                Photo my_photo = response.body().getPhotos().getPhoto().get(0);
                cat.setLink(Config.getUrl(my_photo));
                view.mostViewedCategory(cat);
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.e(" ", t.toString());
            }
        });
    }

    private void fillFirstTimeDb() {
        DatabaseHandler DBH = DatabaseHandler.getdatabaseHandler(mContext);
        for (String cat : Config.CATEGORIES) {
            DBH.addCategory(cat);
        }
    }


}
