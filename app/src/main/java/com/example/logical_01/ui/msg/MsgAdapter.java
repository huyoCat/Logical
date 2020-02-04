package com.example.logical_01.ui.msg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logical_01.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg>mMsgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        public ViewHolder(View view){
            super(view);
            leftLayout=(LinearLayout)view.findViewById(R.id.left_layout);
            rightLayout=(LinearLayout)view.findViewById(R.id.right_layout);
            leftMsg=(TextView)view.findViewById(R.id.left_msg);
            rightMsg=(TextView)view.findViewById(R.id.right_msg);
        }
    }
    public MsgAdapter(List<Msg> msgList){
        mMsgList=msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate
                (R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg msg=mMsgList.get(position);
        if (msg.getType()==Msg.TYPE_RECEIVED){
        //如果是收到消息，显示左边的消息布局，右边布局隐藏
        holder.leftLayout.setVisibility(View.VISIBLE);
        holder.rightLayout.setVisibility(View.GONE);
        holder.leftMsg.setText(msg.getContent());
        }
        else if(msg.getType()==Msg.TYPE_SENT){
            //如果是发出消息，显示右边的消息布局，左边布局隐藏
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
