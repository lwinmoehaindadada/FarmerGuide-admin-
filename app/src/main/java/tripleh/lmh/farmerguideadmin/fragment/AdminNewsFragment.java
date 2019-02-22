package tripleh.lmh.farmerguideadmin.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.LinkedList;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.activity.writepost;
import tripleh.lmh.farmerguideadmin.adapter.AdminPostAdapter;
import tripleh.lmh.farmerguideadmin.model.Post;
import tripleh.lmh.farmerguideadmin.statics.RefStatic;
import tripleh.lmh.farmerguideadmin.utility.Mdetect;
import tripleh.lmh.farmerguideadmin.utility.Rabbit;

public class AdminNewsFragment extends Fragment {
    public LinkedList<String> id = new LinkedList();
    ImageView img_loading;
    private LinearLayoutManager layoutManager;
    LinearLayout loadingLayout;
    private Context mContext;
    private DatabaseReference mDatabase = RefStatic.postRef;
    private AdminPostAdapter postAdapter;
    private LinkedList<Post> postArray = new LinkedList();
    private FloatingActionButton postNews;
    private RecyclerView postRecyclerview;
    private boolean visible;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        this.loadingLayout = (LinearLayout) rootView.findViewById(R.id.linear_loading);
        this.img_loading = (ImageView) rootView.findViewById(R.id.img_loading);
        Glide.with(getActivity()).load(Integer.valueOf(R.drawable.chat_loading_before)).into(this.img_loading);
        this.postAdapter = new AdminPostAdapter(this.mContext, this.postArray, this.visible);
        this.postRecyclerview = (RecyclerView) rootView.findViewById(R.id.postRecyclerview);
        this.layoutManager = new LinearLayoutManager(this.mContext);
        this.postRecyclerview.setLayoutManager(this.layoutManager);
        this.postRecyclerview.setAdapter(this.postAdapter);
        this.postNews = (FloatingActionButton) rootView.findViewById(R.id.cp);
        this.postNews.setVisibility(View.VISIBLE);
        this.postNews.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminNewsFragment.this.startActivity(new Intent(AdminNewsFragment.this.getActivity(), writepost.class));

            }
        });
        this.mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post user = (Post) dataSnapshot.getValue(Post.class);
                if(!Mdetect.isUnicode()) {
                    user.setTitle(Rabbit.uni2zg(user.getTitle()));
                    user.setInfo(Rabbit.uni2zg(user.getInfo()));
                }
                user.setId(dataSnapshot.getKey());
                if (!AdminNewsFragment.this.id.contains(user.getId())) {
                    AdminNewsFragment.this.postArray.add(0, user);
                    AdminNewsFragment.this.id.add(0, dataSnapshot.getKey());
                    AdminNewsFragment.this.loadingLayout.setVisibility(View.INVISIBLE);
                    AdminNewsFragment.this.postAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                int postIndex = AdminNewsFragment.this.id.indexOf(dataSnapshot.getKey());
                if (postIndex > -1) {
                    AdminNewsFragment.this.id.remove(postIndex);
                    AdminNewsFragment.this.postArray.remove(postIndex);
                    AdminNewsFragment.this.postAdapter.notifyItemRemoved(postIndex);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }
}
