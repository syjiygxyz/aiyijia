package com.ayj.aiyijia.fragment.cz;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.hyk.HykZfActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.adapter.cz.CzAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.SmallmoneyFace;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.Constant;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/1.
 * 会员加入充值
 */
public class CzAddCzFragment extends LazyFragment {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.bt_zf)
    TextView mBtZf;
    CzAdapter czAdapter;

    List<SmallmoneyFace.DataBean> list = new ArrayList<>();

    SmallmoneyFace.DataBean item;

    @Override
    public void fetchData() {
        mRlv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        getSmallmoneyFace();
        mRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i <list.size() ; i++) {
                    list.get(i).setTrue(false);
                }
                list.get(position).setTrue(true);
                czAdapter.setNewData(list);
                czAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_czjr_add;
    }

    @OnClick({R.id.bt_zf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_zf:
                for (int i = 0; i <list.size() ; i++) {
                    if(list.get(i).getTrue()){
                        item = list.get(i);
                    }
                }
                if(null==item){
                    showToast("请选择充值金额");
                }else{
                    HykZfActivity.jumpActivity(getActivity(),"充值",item.getFacevalue(),
                            "爱医家", Constant.CZ_DH,"1","","","");
                }

                break;
        }
    }

    private void getSmallmoneyFace(){
        OkHttpUtils.post()
                .url(WebUtils.getRequestUrl(WebUtils.KHQ_CZ))
                .addParams("json","{\"key\":\"\"}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Check check = new Gson().fromJson(response, Check.class);
                        switch (check.getErr()){
                            case 0:
                                SmallmoneyFace smallmoneyFace  = new Gson().fromJson(response,SmallmoneyFace.class);
                                list.addAll(smallmoneyFace.getData());
                                for (int i = 0; i <list.size() ; i++) {
                                    list.get(i).setTrue(false);
                                }
                                if(null==czAdapter){
                                    czAdapter = new CzAdapter(R.layout.cz_item,list);
                                    mRlv.setAdapter(czAdapter);
                                }else{
                                    czAdapter.setNewData(list);
                                }
                                break;
                            default:
                                break;
                        }

                    }
                });
    }
}

