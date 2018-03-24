package manjinder.fragmentproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Manjinder_Singh on 11-Aug-17.
 */
public class FirstFragment extends Fragment{

    ArrayAdapter<String> adapter;
    ArrayList<String> listData;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getArguments().getString("jsonData");
        Log.d("TAG", data);
        listData = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            for(int i=0;i<jsonArray.length();i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                listData.add(title);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.first_fragment_layout, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment secondFragment = new SecondFragment();
                Bundle extraTitle = new Bundle();
                extraTitle.putString("title", listData.get(position));
                secondFragment.setArguments(extraTitle);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.parentView, secondFragment)
                        .commit();
            }
        });

        return rootView;
    }
}
