package vlad.itschool.ru.finalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by Влад on 18.05.2018.
 */

public class Settings extends Activity {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getFragmentManager().beginTransaction().replace(android.R.id.content,new Fragment()).commit();
        }
}

@SuppressLint("ValidFragment")
class Fragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
