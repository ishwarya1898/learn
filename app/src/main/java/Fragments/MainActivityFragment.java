package Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstproject.R;

public abstract class MainActivityFragment extends AppCompatActivity {
    View Fragment1;
    View Fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        Fragment1=findViewById(R.id.fragment1);
        Fragment2=findViewById(R.id.fragment2);
    }


}