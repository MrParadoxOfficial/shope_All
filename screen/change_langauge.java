package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import shope.three3pro.shope_All.R;

public class change_langauge extends AppCompatActivity {
    private CardView english_txt_Card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_change_langauge);
        english_txt_Card=findViewById(R.id.english_txt_Card);
        english_txt_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(change_langauge.this,sign_up_activity.class);
                startActivity(in);
            }
        });
    }
}