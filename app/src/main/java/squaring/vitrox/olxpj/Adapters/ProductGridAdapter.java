package squaring.vitrox.olxpj.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import squaring.vitrox.olxpj.R;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.ViewHolder> {

    private List<String> mDataSet;
    private Context mContext;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String item, ImageView im);
    }


    public ProductGridAdapter(Context context, ProductGridAdapter.OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
        this.mDataSet = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.grid_item_image);
        }

        public void bind(final String urlImage, final ProductGridAdapter.OnItemClickListener listener) {
            Glide.with(mContext).load(urlImage)
                    .diskCacheStrategy( DiskCacheStrategy.NONE)
                    .listener(requestListener)
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                    .centerCrop().into(mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(urlImage,mImageView);
                }
            });

        }
    }

    @Override
    public ProductGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false);
        ProductGridAdapter.ViewHolder vh = new ProductGridAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductGridAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(mDataSet.get(i), listener);
    }

    public void addData(String data) {
        mDataSet.add(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //CAPTURE ERROR ON URL FOR SOME IMAGES THAT ARE NOT AVALIABLE
            Log.d("ImageErrorUrl: ", model);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };
}

