package kr.co.diordna.yourdestiny;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_center;
    private EditText et_name;
    private View fl_confirm;
    private LottieAnimationView animation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        play(0);
    }

    private void initView() {
        tv_center = findViewById(R.id.tv_center);
        et_name = findViewById(R.id.et_name);
        fl_confirm = findViewById(R.id.fl_confirm);
        animation_view = findViewById(R.id.animation_view);
    }

    private void play(int seen) {
        switch (seen) {
            case 0:
                playOne();
                break;
            case 1:
                playTwo();
                break;
            case 2:
                playThree();
                break;
        }

    }

    private void playOne() {
        PlayProcess.one(this, tv_center, new ProcessFinishCallback() {
            @Override
            public void onFinish() {
                play(1);
            }
        });
    }

    private void playTwo() {
        tv_center.setText(getString(R.string.your_name));
        et_name.setVisibility(View.VISIBLE);
        fl_confirm.setVisibility(View.VISIBLE);
        PlayProcess.two(this, tv_center, et_name, fl_confirm);

        fl_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(2);
            }
        });
    }

    private void playThree() {
        PlayProcess.three(this, tv_center,
                et_name,
                fl_confirm,
                animation_view, new ProcessFinishCallback() {
                    @Override
                    public void onFinish() {
                        fl_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("ABC", "끝");
                            }
                        });
                    }
                });
    }

}
