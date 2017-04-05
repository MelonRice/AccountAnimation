package cn.melonrice.accountanimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.cv)
    CardView cv;
    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_login);
        cv.startAnimation(animation);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                                                           Animation.RELATIVE_TO_SELF,
                                                           0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setStartOffset(800);


        fab.startAnimation(scaleAnimation);
    }

    @Override protected void onStart() {
        super.onStart();
        fab.setVisibility(View.INVISIBLE);
    }

    @Override protected void onStop() {
        super.onStop();
        Log.d("mytest","Main stop");
    }

    @Override protected void onPause() {
        super.onPause();
        Log.d("mytest","Main pause");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        Log.d("mytest","Main destroy");
    }

    @Override protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
        Log.d("mytest","Main resume");
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("mytest","Main onConfigurationChanged");
    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:

                //退出本页面时的动画
                getWindow().setExitTransition(null);
                //进入本页面时的动画
                getWindow().setEnterTransition(null);

                /*
                  为了使有一个共享元素的两个activities间使用过渡动画：
                 1.在你的主题中启用窗口内容过渡 windowContentTransitions
                 2.在你的主题样式中指定共享元素的过渡  android:transitionName="loginFab",这个也可以在代码里设置
                 3.定义你的过渡动画为XML资源
                  **/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:

                /*
                  Explde,中间向边缘打开(窗户效果)
                  Slide，从上到下或从左到右层级展开
                  fade，显示隐藏淡入淡出
                  **/
                Explode explode = new Explode();

                //效果持续时间
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                //ActivityOptionsCompat相比ActivityOptions，多了对android4.x的兼容

                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent i2 = new Intent(this, LoginSuccessActivity.class);
                startActivity(i2, oc2.toBundle());
                break;
        }
    }
}
