package animation;

import android.app.Activity;
import android.content.Context;

import com.example.shoespricecomparision.R;

public class Animation {

    private int onStartCount = 1;

    public Animation(){}

    public void slideRight(Activity activity){
        if (onStartCount == 1) // 1st time
        {
            activity.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        } else
        {
            onStartCount = 0;
        }
    }


    public void slideLeft(Activity activity){
        if (onStartCount == 1) // 1st time
        {
            activity.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else
        {
            onStartCount = 0;
        }
    }



}
