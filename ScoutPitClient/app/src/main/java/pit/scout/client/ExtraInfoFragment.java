package pit.scout.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.io.PrintWriter;

/**
 * Created by mnenmenth on 3/8/15.
 */
public class ExtraInfoFragment extends Fragment {

    public ExtraInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_extra_info, container, false);

        return rootView;
    }

}
