package squaring.vitrox.olxpj.Network;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import squaring.vitrox.olxpj.Model.FlickrResponse;

public interface ApiInterface {

    @GET("rest/?method=flickr.photos.search")
    Call<FlickrResponse> getPhotoList(@Query("tags") String category, @Query("page") int i);

    @GET("rest/?method=flickr.photos.search")
    Call<FlickrResponse> getidPhotoCategory(@Query("tags") String category, @Query("page") int page);

}