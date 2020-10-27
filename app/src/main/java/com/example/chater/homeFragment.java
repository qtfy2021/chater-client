package com.example.chater;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.listener.SelectViewOnclickListener;

public class homeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SelectView selectview1;
    private OnFragmentInteractionListener mListener;

    public homeFragment() {

        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        final SelectView selectview1 = getView().findViewById(R.id.selecteview_item);
        selectview1.setImageId(R.drawable.icon_);
        selectview1.setFromID("123");
//        selectview1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent i = new Intent(getActivity(), chatHubActivity.class);
//                i.putExtra("userID", selectview1.getFromID());
//                startActivity(i);
//            }
//        });

        selectview1.setViewOnclickClickListener(new SelectViewOnclickListener(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //接受activty传来的消息



    //在Activity中加载fragment时会要求实现onFragmentInteraction(Uri uri)方法
    //此方法主要作用是从fragment向activity传递数据
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
