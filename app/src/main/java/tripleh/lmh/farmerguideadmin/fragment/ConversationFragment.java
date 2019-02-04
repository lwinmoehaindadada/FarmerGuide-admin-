package tripleh.lmh.farmerguideadmin.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import java.util.List;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.adapter.ChatAdapter;
import tripleh.lmh.farmerguideadmin.model.Chat;
import tripleh.lmh.farmerguideadmin.statics.RefStatic;

public class ConversationFragment extends Fragment {
    public static String cityNow = "pakokku";
    Editor adEdit;
    SharedPreferences adPref;
    ChatAdapter adapter;
    RecyclerView chatRecycler;
    List<Chat> chats;
    ImageView img_loading;
    LinearLayout loadingLayout;
    private Context mContext;
    LinearLayoutManager manager;
    MediaPlayer mp;
    private RadioGroup radioGroup;
    private RadioButton radioPakokku;
    private RadioButton radioYesagyo;
    DatabaseReference ref = null;
    //SwipeController swipeController;

    class RadioGroupClickListener implements OnCheckedChangeListener {
        RadioGroupClickListener() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == ConversationFragment.this.radioPakokku.getId()) {
                if (!ConversationFragment.cityNow.equals("pakokku")) {
                    ConversationFragment.this.chats.clear();
                    ConversationFragment.this.adapter.notifyDataSetChanged();
                    ConversationFragment.cityNow = "pakokku";
                    ChatAdapter.city = ConversationFragment.cityNow;
                    ConversationFragment.this.refListen(ConversationFragment.cityNow);
                }
            } else if (!ConversationFragment.cityNow.equals("yesagyo")) {
                ConversationFragment.this.chats.clear();
                ConversationFragment.this.adapter.notifyDataSetChanged();
                ConversationFragment.cityNow = "yesagyo";
                ChatAdapter.city = ConversationFragment.cityNow;
                ConversationFragment.this.refListen(ConversationFragment.cityNow);
            }
        }
    }

    class ChatListener implements ChildEventListener {
        ChatListener() {
        }

        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Chat chat = (Chat) dataSnapshot.getValue(Chat.class);
            ConversationFragment.this.adapter.addToTop(chat);
            Log.i(DataBufferSafeParcelable.DATA_FIELD, "date added");
            Log.i("Chat Data", chat.toString());
            ConversationFragment.this.loadingLayout.setVisibility(View.INVISIBLE);
            ConversationFragment.this.adapter.notifyDataSetChanged();
        }

        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Chat chat = (Chat) dataSnapshot.getValue(Chat.class);
            Log.i("change", "data changed");
            for (int i = 0; i < ConversationFragment.this.chats.size(); i++) {
                if (((Chat) ConversationFragment.this.chats.get(i)).getId().equals(chat.getId())) {
                    if (((Chat) ConversationFragment.this.chats.get(i)).getLastmessage().equals(chat.getLastmessage())) {
                        ConversationFragment.this.adapter.changeIndexData(chat, i);
                        Log.i("change", "other data changed");
                    } else {
                        Log.i("change", "messagechanged");
                        ConversationFragment.this.adapter.removeItem(i);
                        ConversationFragment.this.adapter.addToTop(chat);
                        ConversationFragment.this.mp.start();
                    }
                }
            }
        }

        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            Chat chat = (Chat) dataSnapshot.getValue(Chat.class);
            for (int i = 0; i < ConversationFragment.this.chats.size(); i++) {
                if (((Chat) ConversationFragment.this.chats.get(i)).getId().equals(chat.getId())) {
                    ConversationFragment.this.adapter.removeItem(i);
                }
            }
        }

        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Chat chat = dataSnapshot.getValue(Chat.class);
        }

        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.i("chat err", "database error");
        }
    }



    public ConversationFragment() {
        List arrayList = new ArrayList();
        this.chats = arrayList;
        this.chats = arrayList;
    }

    @SuppressLint("ValidFragment")
    public ConversationFragment(Context context) {
        List arrayList = new ArrayList();
        this.chats = arrayList;
        this.chats = arrayList;
        this.mContext = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        this.adPref = getActivity().getSharedPreferences("adpref", 0);
        this.adEdit = this.adPref.edit();
        this.ref = RefStatic.chatRef;
        this.mp = MediaPlayer.create(getActivity(), R.raw.stairs);
        this.radioGroup = (RadioGroup) view.findViewById(R.id.radio_city);
        this.radioPakokku = (RadioButton) view.findViewById(R.id.radio_pku);
        this.radioYesagyo = (RadioButton) view.findViewById(R.id.radio_yso);
        this.chatRecycler = (RecyclerView) view.findViewById(R.id.chat_recycler);
        this.manager = new LinearLayoutManager(getActivity());
        this.adapter = new ChatAdapter(this.chats, getActivity());
        this.chatRecycler.setAdapter(this.adapter);
        this.chatRecycler.setLayoutManager(this.manager);

        this.loadingLayout = (LinearLayout) view.findViewById(R.id.linear_loading);
        this.img_loading = (ImageView) view.findViewById(R.id.img_loading);
        Glide.with(getActivity()).load(Integer.valueOf(R.drawable.chat_loading_before)).into(this.img_loading);
        refListen(cityNow);
        this.radioGroup.setOnCheckedChangeListener(new RadioGroupClickListener());
        return view;
    }

    private void refListen(String cityNow) {
        this.ref.child("pakokku").orderByChild("date").addChildEventListener(new ChatListener());
    }

}
