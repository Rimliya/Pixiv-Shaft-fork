package ceui.lisa.activities;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

import ceui.lisa.R;
import ceui.lisa.databinding.ActivityOutWakeBinding;
import ceui.lisa.interfaces.Callback;
import ceui.lisa.utils.Params;
import ceui.lisa.utils.PixivOperate;

import static ceui.lisa.activities.Shaft.sUserModel;

public class OutWakeActivity extends BaseActivity<ActivityOutWakeBinding> {

    @Override
    protected int initLayout() {
        return R.layout.activity_out_wake;
    }

    @Override
    public boolean hideStatusBar() {
        return true;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {

                String scheme = uri.getScheme();
                if (!TextUtils.isEmpty(scheme)) {

                    if (uri.getPath() != null) {
                        if (uri.getPath().contains("artworks")) {
                            List<String> pathArray = uri.getPathSegments();
                            String illustID = pathArray.get(pathArray.size() - 1);
                            if (!TextUtils.isEmpty(illustID)) {
                                PixivOperate.getIllustByID(Shaft.sUserModel, Integer.valueOf(illustID), mContext, new Callback<Void>() {
                                    @Override
                                    public void doSomething(Void t) {
                                        finish();
                                    }
                                });
                                return;
                            }
                        }

                        if (uri.getPath().contains("users")) {
                            List<String> pathArray = uri.getPathSegments();
                            String userID = pathArray.get(pathArray.size() - 1);
                            if (!TextUtils.isEmpty(userID)) {
                                Intent userIntent = new Intent(mContext, UserActivity.class);
                                userIntent.putExtra(Params.USER_ID, Integer.valueOf(userID));
                                startActivity(userIntent);
                                return;
                            }
                        }
                    }


                    //http网页跳转到这里
                    if (scheme.contains("http")) {
                        String illustID = uri.getQueryParameter("illust_id");
                        if (!TextUtils.isEmpty(illustID)) {
                            PixivOperate.getIllustByID(Shaft.sUserModel, Integer.valueOf(illustID), mContext, new Callback<Void>() {
                                @Override
                                public void doSomething(Void t) {
                                    finish();
                                }
                            });
                            return;
                        }

                        String userID = uri.getQueryParameter("id");
                        if (!TextUtils.isEmpty(userID)) {
                            Intent userIntent = new Intent(mContext, UserActivity.class);
                            userIntent.putExtra(Params.USER_ID, Integer.valueOf(userID));
                            startActivity(userIntent);
                            finish();
                            return;
                        }

                    }

                    //pixiv内部链接，如 pixiv://illusts/73190863
                    if (scheme.contains("pixiv")) {
                        String host = uri.getHost();
                        if (!TextUtils.isEmpty(host)) {
                            if (host.contains("users")) {
                                String path = uri.getPath();
                                Intent userIntent = new Intent(mContext, UserActivity.class);
                                userIntent.putExtra(Params.USER_ID, Integer.valueOf(path.substring(1)));
                                startActivity(userIntent);
                                finish();
                                return;
                            }

                            if (host.contains("illusts")) {
                                String path = uri.getPath();
                                PixivOperate.getIllustByID(Shaft.sUserModel, Integer.valueOf(path.substring(1)),
                                        mContext, t -> finish());
                                return;
                            }

                            if (host.contains("novels")) {
                                String path = uri.getPath();
                                PixivOperate.getNovelByID(Shaft.sUserModel, Integer.valueOf(path.substring(1)),
                                        mContext, t -> finish());
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (sUserModel != null && sUserModel.getResponse().getUser().isIs_login()) {
            Intent i = new Intent(mContext, MainActivity.class);
            mActivity.startActivity(i);
            mActivity.finish();
        } else {
            Intent i = new Intent(mContext, TemplateActivity.class);
            i.putExtra(TemplateActivity.EXTRA_FRAGMENT, "登录注册");
            startActivity(i);
            finish();
        }
    }
}
