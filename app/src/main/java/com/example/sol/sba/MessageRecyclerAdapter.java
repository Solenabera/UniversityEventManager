package com.example.sol.sba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageRecyclerAdapter  extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder>{

    public List<AdminMessage> messageList;
    public List<User> user_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    public MessageRecyclerAdapter(List<AdminMessage> messageList,List<User> user_list){

        this.messageList = messageList;
        this.user_list = user_list;

    }

    public MessageRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageRecyclerAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        String commentMessage = messageList.get(position).getMessage();
        holder.setComment_message(commentMessage);

        String userName = user_list.get(position).getName();
        String userImage =user_list.get(position).getImage();

        holder.setUserData(userName, userImage);

    }

    @Override
    public int getItemCount() {

        if(messageList != null) {

            return messageList.size();

        } else {

            return 0;

        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView comment_message;
        private TextView comment_uName;
        private CircleImageView commentUserImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setComment_message(String message) {

            comment_message = mView.findViewById(R.id.comment_message);
            comment_message.setText("Sol");

        }


        public void setUserData(String name, String image){

            commentUserImage = mView.findViewById(R.id.comment_image);
            comment_uName = mView.findViewById(R.id.comment_username);

            comment_uName.setText(name);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.profile_placeholder);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(image).into(commentUserImage);

        }

    }
}

