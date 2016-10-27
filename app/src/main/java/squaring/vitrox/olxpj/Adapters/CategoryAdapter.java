package squaring.vitrox.olxpj.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mDataSet;
    private Context mContext;
    private final OnItemClickListener listener;
    private int max;
    public interface OnItemClickListener {
        void onItemClick(Category item);
    }

    public CategoryAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
        this.mDataSet = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textCategory);
            mImageView = (ImageView) v.findViewById(R.id.imageCategory);
        }

        public void bind(final Category item, final OnItemClickListener listener) {
            mTextView.setText(item.getCategoryname());
            String Urlimage = item.getLink();
            Glide.with(mContext).load(Urlimage)
                    .skipMemoryCache(true)
                    .listener(requestListener)
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                    .centerCrop().into(mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(mDataSet.get(i), listener);
    }

    public void addData(Category data) {
        mDataSet.add(data);
        if (mDataSet.size()==6)
        {
            orderDataset();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public void orderDataset()
    {

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