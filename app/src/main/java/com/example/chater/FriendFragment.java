package com.example.chater;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ViewAdapter.FriendListAdapter;
import com.example.contacts.FriendListFragmentContact;
import com.example.model.Entity.User;
import com.example.model.Entity.middleware.FriendListItem;
import com.example.model.Entity.middleware.NewFriend;
import com.example.presenter.LoadFriendListPresenterImpl;
import com.example.until.AppContext;
import com.example.until.ToastUntil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FriendFragment extends Fragment implements FriendListFragmentContact.FriendFragmentInt {

    //view
    @BindView(R.id.friend_list_recycleview)
    RecyclerView friendListRv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<FriendListItem> friendList;

    //presenter
    FriendListFragmentContact.LoadFriendListPresenter loadFriendListPresenter;

    //Adapter
    FriendListAdapter friendListAdapter;

    private OnFragmentInteractionListener mListener;

    public FriendFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//我们可以知道Activity重新创建时，会重新构建它所管理的Fragment，原先的Fragment的字段值将会全部丢失，
// 但是通过 Fragment.setArguments(Bundle bundle)方法设置的bundle会保留下来。
// 所以尽量使用Fragment.setArguments(Bundle bundle)方式来传递参数
    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(this, getView());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        friendListRv.setLayoutManager(linearLayoutManager);
        loadFriendListPresenter = new LoadFriendListPresenterImpl(this);
        initRecycleView();
        initListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_friend, container, false);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.friend_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.friend_fragment_menu_addFriend:
                Intent intent = new Intent(getActivity(), FindFriendActivity.class);
                getActivity().startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initRecycleView(){
        //
        friendList = new LinkedList<>();
        User user = new User();
        user.setUserName("张京华");
        User user2 = new User();
        user.setUserName("花守海露希");
        User user3 = new User();
        user.setUserName("凤玲天天");
        friendList.add(new FriendListItem(user));
        friendList.add(new FriendListItem(user2));
        friendList.add(new FriendListItem(new NewFriend(user3)));
        //

        friendListAdapter = new FriendListAdapter(getContext(), friendList);
        friendListRv.setAdapter(friendListAdapter);
        loadFriendListPresenter.LoadFriend(friendList);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void UpDateFriendList(List list) {

        friendListAdapter.setItemList(list);
        friendListAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowToast(String s) {
        ToastUntil.showToast(s, getContext());
    }

    @Override
    public void StartNewActivity(Intent intent) {
        startActivity(intent);
    }


    private void initListener(){

        friendListAdapter.setOnItemClickListener(position -> {

            loadFriendListPresenter.StartActivity(chatHubActivity.class, position);
            Log.d("FriendFragment", "选择：" + friendList.get(position).getUser().getUserNameString() );
        });

        friendListAdapter.setAddFriendConfirmBtnClickListener(new FriendListAdapter.AddFriendConfirmBtnClickListener() {
            @Override
            public void onAdd(int position) {
                Logger.d("点击了接受" + friendList.get(position).getNewFriend().getUser().getUserNameString());
            }

            @Override
            public void onCancel(int postion) {

            }
        });
    }

}
