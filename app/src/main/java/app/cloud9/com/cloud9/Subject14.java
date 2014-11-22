package app.cloud9.com.cloud9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chirag on 06-11-2014.
 */
public class Subject14 extends Fragment {

    JSONObject full_json;
    JSONObject json_current_subject;

    String all_marks_string;
    String subject_name_string;

    String current_subject_string;

    TextView i1;
    TextView i2;
    TextView i3;

    TextView l1;
    TextView l2;

    TextView el;

    TextView q1;
    TextView q2;

    TextView lh;
    TextView la;

    TextView th;
    TextView ta;

    TextView final_cie_marks;

    //Strings
    String s_i1;
    String s_i2;
    String s_i3;

    String s_l1;
    String s_l2;

    String s_el;

    String s_q1;
    String s_q2;

    String s_lh;
    String s_la;

    String s_th;
    String s_ta;

    String s_final_cie_marks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View d1 = inflater.inflate(R.layout.display_marks_and_attendance, container, false);

        i1 = (TextView) d1.findViewById(R.id.i1);
        i2 = (TextView) d1.findViewById(R.id.i2);
        i3 = (TextView) d1.findViewById(R.id.i3);


        q1 = (TextView) d1.findViewById(R.id.q1);
        q2 = (TextView) d1.findViewById(R.id.q2);

        la = (TextView) d1.findViewById(R.id.la);
        lh = (TextView) d1.findViewById(R.id.lh);

        ta = (TextView) d1.findViewById(R.id.ta);
        th = (TextView) d1.findViewById(R.id.th);

        final_cie_marks = (TextView) d1.findViewById(R.id.final_cie_marks);
        el = (TextView) d1.findViewById(R.id.el);

        l1 = (TextView) d1.findViewById(R.id.l1);
        l2 = (TextView) d1.findViewById(R.id.l2);


        Temp activity = (Temp) getActivity();

        String all_marks_string = activity.getAllMarks();

        String subject_name_string = activity.getPageTitle(13);


        try {
            full_json = new JSONObject(all_marks_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            current_subject_string = full_json.getString(subject_name_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json_current_subject = new JSONObject(current_subject_string);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Get 3 internals marks

        //First Internal
        try {
            s_i1 = json_current_subject.getString("internal_1");
            i1.setText("1st Internal marks : " + s_i1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Second Internal
        try {
            s_i2 = json_current_subject.getString("internal_2");
            i2.setText("2nd Internal marks : " + s_i2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_i3 = json_current_subject.getString("internal_3");
            i3.setText("3rd Internal marks : " + s_i3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Quizzes


        try {
            s_q1 = json_current_subject.getString("quiz_1");
            q1.setText("Quiz 1 marks : " + s_q1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_q2 = json_current_subject.getString("quiz_2");
            q2.setText("Quiz 2 marks : " + s_q2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Lab Internals
        try {
            s_l1 = json_current_subject.getString("lab_internal_1");
            l1.setText("Lab 1 : " + s_l1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_l2 = json_current_subject.getString("lab_internal_2");
            l2.setText("Lab 2 : " + s_l2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Theory Classes
        try {
            s_th = json_current_subject.getString("theory_classes_held");
            th.setText("Theory Held " + s_th);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_ta = json_current_subject.getString("theory_classes_attended");
            ta.setText("Theory Attended : " + s_ta);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Enternal Lab

        try {
            s_el = json_current_subject.getString("lab_external");
            el.setText("Lab External : " + s_el);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Final Cie Marks

        try {
            s_final_cie_marks = json_current_subject.getString("final_cie_marks");
            final_cie_marks.setText("Final cie marks : " + s_final_cie_marks);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Lab Held and Attended

        try {
            s_lh = json_current_subject.getString("lab_held");
            lh.setText("Lab Held : " + s_lh);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            s_la = json_current_subject.getString("lab_attended");
            la.setText("Lab Attended : " + s_la);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return d1;

    }
}

