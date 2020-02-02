package com.example.logical_01.ui.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logical_01.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book>mBookList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookName;
        public ViewHolder(View view){
            super(view);
            bookName=(TextView)view.findViewById(R.id.text_book);
        }
    }
    public BookAdapter(List<Book>bookList){
        mBookList=bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book=mBookList.get(position);
        holder.bookName.setText(book.getName());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
