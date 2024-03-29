package com.example.liksi.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Interface.OnItemClickListener;
import com.example.liksi.Models.CategoryModel;
import com.example.liksi.R;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {

    Context context;
    List<CategoryModel> cats;
    private OnItemClickListener listener;

    public AdapterCategory(Context context, List<CategoryModel> cats) {
        this.context = context;
        this.cats = cats;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_template, null);
        return new AdapterCategory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategory.ViewHolder holder, int position) {
        CategoryModel cat = cats.get(position);
        holder.cat.setText(cat.getName());
        holder.des.setText(cat.getDescription());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog lDialog = new Dialog(context);
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.copyFrom(lDialog.getWindow().getAttributes());
                lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lWindowParams.horizontalMargin = (int) context.getResources().getDimension(R.dimen.dialog_margin);
                lDialog.setContentView(R.layout.fragment_create_category);
                lDialog.setCancelable(true);

                Drawable backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.dialog_background);
                lDialog.getWindow().setBackgroundDrawable(backgroundDrawable);

                lDialog.getWindow().getDecorView().setPadding(
                        (int) context.getResources().getDimension(R.dimen.dialog_margin), 0,
                        (int) context.getResources().getDimension(R.dimen.dialog_margin), 0
                );

                lDialog.show();
                lDialog.getWindow().setAttributes(lWindowParams);

                EditText cate = lDialog.findViewById(R.id.categoryname);
                EditText des = lDialog.findViewById(R.id.categorydesc);
                Button save = lDialog.findViewById(R.id.button);
                cate.setText(cat.getName());
                des.setText(cat.getDescription());
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CategoryModel cat1 = new CategoryModel();
                        cat1.setName(cate.getText().toString());
                        cat1.setDescription(des.getText().toString());
                        cat1.setCatId(cat.getCatId());

                        if(cate.getText().toString().trim().equals("") || des.getText().toString().trim().equals(""))
                        {
                            Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        AppDatabase app = AppDatabase.getInstance(context);
                        app.categoryDao().UpdateCategory(cat1);
                        lDialog.dismiss();
                        cats = app.categoryDao().Categories();
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.gotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemRightDrawableClick(cat); // Pass the category to the listener
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase app = AppDatabase.getInstance(context);
                app.categoryDao().DeleteCategory(cat);
                cats = app.categoryDao().Categories();
                notifyDataSetChanged();
            }
        });
    }
    public interface OnItemClickListener {
        void onItemRightDrawableClick(CategoryModel category);
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView des;
        TextView cat;
        ImageView edit, delete, gotos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat = itemView.findViewById(R.id.categoryname);
            des = itemView.findViewById(R.id.categorydesc);
            edit = itemView.findViewById(R.id.editCatBtn);
            delete = itemView.findViewById(R.id.deleteCatBtn);
            gotos = itemView.findViewById(R.id.todos);
        }
    }
}
