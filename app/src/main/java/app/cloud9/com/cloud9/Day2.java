package app.cloud9.com.cloud9;

/**
 * Created by chirag on 20/11/14.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chirag on 06-11-2014.
 */
public class Day2 extends Fragment {

    String[] l1 = {"Do Engineering", "YTBA", "YTBA", "YTBA", "YTBA", "YTBA", "YTBA", "The Freedom Lab-in-a-Box ", "Yet To be Announced", "Technology a Generation ahead ", "IonCUDOSÂ© - Automation of Curriculum Design & Attainment for OBE "};
    List<String> mList = new ArrayList<String>(Arrays.asList(l1));


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View d1 = inflater.inflate(R.layout.day2, container, false);


        return d1;


    }

}