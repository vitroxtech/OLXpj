package squaring.vitrox.olxpj.Presenter;


import android.content.Context;
import android.util.Log;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Helper.RandomHelper;
import squaring.vitrox.olxpj.Model.FlickrResponse;
import squaring.vitrox.olxpj.Model.Photo;
import squaring.vitrox.olxpj.Network.ApiClient;
import squaring.vitrox.olxpj.Network.ApiInterface;

public class ProductsActivityPresenterImp implements ProductsActivityPresenter {

    private Context mContext;
    private ProductsActivityPresenter.view view;
    private RandomHelper rd;
    private int perpage;
    private int pages;

    public ProductsActivityPresenterImp(ProductsActivityPresenter.view v, Context context) {
        this.mContext = context;
        this.view = v;
    }

    /*
        the Random... is made using the logic: first call get a photo from the first 10 pages randomly, then once that is made
        on the response we know how many pages are and how many per page then it's made a even more randomly for the next 9 pics.
        I also set the pages-1 because maybe the las page not contain the same perpage so it can generate random error in limit case
     */
    @Override
    public void onLoad(final String selectedCategory) {
        rd = new RandomHelper();
        perpage = 10;
        pages = 1;
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FlickrResponse> call = apiService.getPhotoList(selectedCategory, rd.getRandomIntInRange(1, pages));
        call.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Response<FlickrResponse> response, Retrofit retrofit) {
                perpage = response.body().getPhotos().getPerpage();
                pages = response.body().getPhotos().getPages();
                Photo myphoto = response.body().getPhotos().getPhoto().get(rd.getRandomIntInRange(1, perpage));
                String st = Config.getUrl(myphoto);
                view.onProductReturn(st);
                for (int i = 0; i < 9; i++) {
                    Call<FlickrResponse> call = apiService.getPhotoList(selectedCategory, rd.getRandomIntInRange(1, pages - 1));
                    call.enqueue(new Callback<FlickrResponse>() {
                        @Override
                        public void onResponse(Response<FlickrResponse> response, Retrofit retrofit) {
                            perpage = response.body().getPhotos().getPerpage();
                            pages = response.body().getPhotos().getPages();
                            Photo myphoto = response.body().getPhotos().getPhoto().get(rd.getRandomIntInRange(1, perpage-1));
                            String st = Config.getUrl(myphoto);
                            view.onProductReturn(st);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            // Log error here since request failed
                            Log.e("ERROR: ", t.toString());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.e("ERROR: ", t.toString());
            }
        });


    }

    @Override
    public void onItemClick(String url) {

    }


}
