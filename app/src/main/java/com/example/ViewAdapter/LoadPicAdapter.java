package com.example.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chater.R;
import com.example.model.Entity.middleware.LoadFileVo;
import com.example.until.ToastUntil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//CREATE BY SHAY 2020/8/2

public class LoadPicAdapter extends RecyclerView.Adapter<LoadPicAdapter.MyViewHolder> {

    Context context;
    int picNum = 8;
    View view;
    List<LoadFileVo> fileList = null;

    OnItemClickListener onItemClickListener;

    public LoadPicAdapter(Context context, List<LoadFileVo> fileList){
        this.context = context;
        this.fileList = fileList;
    }

    public LoadPicAdapter(Context context, List<LoadFileVo> fileList, int picNum){
        this.context = context;
        this.fileList = fileList;
        this.picNum = picNum;
    }


    public interface OnItemClickListener {
        void click(View view, int position);

        void del(View view);
    }

    public void setListener(OnItemClickListener listener){
        onItemClickListener = listener;

    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //图片
        // /*此处为图片加载layout*/
        view = LayoutInflater.from(context).inflate(R.layout.update_pic_item, parent, false);
        return new MyViewHolder(view);
    }


    //设定myviewhlder的响应
    //position为图片顺序数
    public void onBindViewHolder(MyViewHolder holder, final int position){
        if(position == 0 && fileList.get(position).getBitmapArray() == null){//默认第一张
            //加号
            holder.ivPic.setImageResource(R.drawable.ic_add_box_black_24dp);
            holder.ivPic.setOnClickListener(v -> onItemClickListener.click(view, position));
            //删除
            holder.imageViewDel.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp);



            holder.imageViewDel.setVisibility(View.INVISIBLE);
            holder.bg_progressbar.setVisibility(View.GONE);

        }else{
            //压缩
            holder.ivPic.setImageBitmap(fileList.get(position).getBitmap());

            holder.imageViewDel.setVisibility(View.VISIBLE);
            holder.bg_progressbar.setVisibility(View.VISIBLE);
            holder.bg_progressbar.setProgress(fileList.get(position).getProgress());
        }


        holder.imageViewDel.setOnClickListener(v -> {

            if (fileList.get(position).isUpload()){
                ToastUntil.showToast("已上传", context);
            }else {
                fileList.remove(position);
                if (fileList.size() == picNum - 1 && fileList.get(0).getBitmap() != null){
                    fileList.add(0, new LoadFileVo());
                }

                notifyDataSetChanged();
                if (onItemClickListener != null){
                    onItemClickListener.del(view);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    //设定recycleview的item
    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivPic)
        ImageView ivPic;
        @BindView(R.id.ivDel)
        ImageView imageViewDel;
        @BindView(R.id.bg_progressbar)
        ProgressBar bg_progressbar;

        View view;

        MyViewHolder(View view){
            super(view);
            this.view = view;
            //绑定处理
            ButterKnife.bind(this, view);
        }
    }


}
