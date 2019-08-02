package ir.tokaterm.speedview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private SpeedView speedView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedView=findViewById(R.id.speedView);
        speedView.setMaxSpeed(50f);
        speedView.speedTo(18.5f);


    }
}
