package com.ayj.aiyijia.view.passwordinputdialog.view;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.view.passwordinputdialog.KeyBoardAdapter;
import com.ayj.aiyijia.view.passwordinputdialog.PassWordDialog;
import com.ayj.aiyijia.view.passwordinputdialog.impl.DialogCompleteListener;
import com.ayj.aiyijia.view.passwordinputdialog.impl.PassWordCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fizzer on 2017/4/18.
 * Email: doraemonmqq@sina.com
 */

public class PassWordInputView {

    private String[] num = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""};

    private List<String> list = new ArrayList<>();

    private GridView mGridView;
    private PassWordView mPassWordView;
    private Context mContext;
    private LinkedList<String> pswList;
    private TextView mTitle;
    private TextView mSubTitle;
    private TextView mMoney;
    private PassWordDialog mDialog;
    private LinearLayout mCloseDialog;

    DialogCompleteListener mListener;

    public PassWordInputView(Context context, View view, PassWordDialog dialog) {
        mContext = context;
        mDialog = dialog;
        initView(view);
    }

    private void initView(View view) {
        list.add("0");list.add("6");
        list.add("1");list.add("7");
        list.add("2");list.add("8");
        list.add("3");list.add("9");
        list.add("4");list.add("5");
        Collections.shuffle(list);
        list.add(9,"");
        list.add(11,"");
        mGridView = (GridView) view.findViewById(R.id.KeyBordView);
        mPassWordView = (PassWordView) view.findViewById(R.id.PwdView);
        mTitle = (TextView) view.findViewById(R.id.tvTitle);
        mSubTitle = (TextView) view.findViewById(R.id.tvSubTitle);
        mMoney = (TextView) view.findViewById(R.id.tvPayMoney);
        mCloseDialog = (LinearLayout) view.findViewById(R.id.ivDialogClose);

        mCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mGridView.setAdapter(new KeyBoardAdapter(mContext, list));
        pswList = new LinkedList<>();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 9) {    //点击空白的地方不做任何操作
                    return;
                } else if (position == 11) {    //点击回退按钮删除
                    if (pswList.size() == 0) {
                        return;
                    }
                    pswList.removeLast();
                } else {    //点击数字，显示在密码行
                    pswList.add(list.get(position));
                }
                mPassWordView.setPassWord(pswList.size());

            }
        });

        mPassWordView.setCompleteListener(new PassWordCompleteListener() {
            @Override
            public void complete() {
                if (mListener != null) {
                    mListener.dialogCompleteListener(mMoney.getText().toString(), listToString(pswList));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    },100);

                }
            }
        });
    }

    /**
     * 设置页面主标题
     * @param msg   msg
     */
    public void setTitle(String msg) {
        setText(mTitle,msg);
    }

    /**
     * 设置副标题
     * @param msg   msg
     */
    public void setSubTitle(String msg) {
        setText(mSubTitle,msg);
    }

    /**
     * 设置交易金额
     * @param msg msg
     */
    public void setMoney(String msg){
        setText(mMoney,msg);
    }

    /**
     * 设置文字
     * @param tv tv
     * @param msg msg
     */
    private void setText(TextView tv, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        tv.setText(msg);
    }

    /**
     * 设置密码输入完成时的监听
     *
     * @param listener
     */
    public void setDialogCompleteListener(DialogCompleteListener listener) {
        mListener = listener;
    }

    /**
     * 将list转换为String
     *
     * @param list
     * @return
     */
    private String listToString(LinkedList<String> list) {
        StringBuilder result = new StringBuilder();
        if (list != null) {
            for (String s : list) {
                result.append(s);
            }
        }

        return result.toString();
    }
}
