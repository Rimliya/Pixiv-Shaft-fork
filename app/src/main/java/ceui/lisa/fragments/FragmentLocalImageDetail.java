package ceui.lisa.fragments;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import ceui.lisa.R;
import ceui.lisa.databinding.FragmentImageDetailBinding;
import ceui.lisa.utils.Params;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FragmentLocalImageDetail extends BaseFragment<FragmentImageDetailBinding> {

    private String filePath;

    public static FragmentLocalImageDetail newInstance(String filePath) {
        Bundle args = new Bundle();
        args.putString(Params.FILE_PATH, filePath);
        FragmentLocalImageDetail fragment = new FragmentLocalImageDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        filePath = bundle.getString(Params.FILE_PATH);
    }

    @Override
    public void initLayout() {
        mLayoutID = R.layout.fragment_image_detail;
    }

    @Override
    public void initView() {
        Glide.with(mContext)
                .load(filePath)
                .transition(withCrossFade())
                .into(baseBind.illustImage);
        baseBind.progress.setVisibility(View.INVISIBLE);
    }
}
