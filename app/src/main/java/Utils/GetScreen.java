package Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class GetScreen {
    private int screenWidth;
    private int screenHeight;
    private Context context;
    public   GetScreen(Context context){
        this.context=context;
        getscreen(context);
    }
    public  int getScreenWith(){
        return screenWidth;
    }
    public  int getScreenHeight(){
        return screenHeight;
    }
    private void getscreen(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
         screenWidth = (int) (width / density);  // 屏幕宽度(dp)
         screenHeight = (int) (height / density);// 屏幕高度(dp)
    }
}
