package com.example.zyh.jxnudiscuss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zyh on 17-3-11.
 */

public class NewThemeActivity extends Activity {
    private TextView  themeactiivty_theme_title_tv;
    private  TextView themeactiivty_theme_content_tv;
    private Button  themeactivity_theme_commit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newthemeactivity);
        themeactiivty_theme_title_tv=(TextView)findViewById(R.id.newthemeactivity_title_edit);
        themeactiivty_theme_content_tv=(TextView)findViewById(R.id.newthemeactivity_content_edit);
        themeactivity_theme_commit_btn=(Button) findViewById(R.id.newthemeactivity_theme_commit);
        themeactivity_theme_commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(themeactiivty_theme_content_tv.getText().toString().length()==0 || themeactiivty_theme_title_tv
                        .getText().toString().length()==0)
                {
                    Toast.makeText(NewThemeActivity.this,"空的标题或内容",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent =new Intent();
                    intent.putExtra("title",themeactiivty_theme_title_tv.getText().toString());
                    intent.putExtra("content",themeactiivty_theme_content_tv.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();

                }

            }
        });

    }
}
