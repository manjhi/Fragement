package manjinder.fragmentproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Manjinder_Singh on 11-Aug-17.
 */
public class SecondFragment extends Fragment{

    String data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String dataFromArgs = getArguments().getString("title");
        data = dataFromArgs;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.second_fragment_layout, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.changeme);
        text.setText(data);
        return rootView;
    }


}
