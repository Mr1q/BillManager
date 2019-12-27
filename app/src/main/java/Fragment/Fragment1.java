package Fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.billmanger.CallBackvalue;
import com.example.hp.billmanger.In_output;
import com.example.hp.billmanger.Income;
import com.example.hp.billmanger.IncomeAdapter;
import com.example.hp.billmanger.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 收入碎片化布局
 */
public class Fragment1 extends Fragment {

    private RecyclerView recyclerView;
    private List<Income> incomes = new ArrayList<>();
    private View view;
    private String name1;
    private TextView textView;
    private ImageView imageView;
    private EditText editmy;
    private CallBackvalue mcallback;
    private IncomeAdapter incomeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.input_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        inits();
        incomeAdapter = new IncomeAdapter(incomes);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(incomeAdapter);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        incomeAdapter.setOnItemClick(new IncomeAdapter.MyItemClick() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(getActivity(), incomes.get(postion).getName(), Toast.LENGTH_SHORT).show();
                textView.setText(incomes.get(postion).getName());
                imageView.setImageResource(incomes.get(postion).getImageId());
                if (incomes.get(postion).getName().equals("其他")) {
                    editmy.setVisibility(View.VISIBLE);
                } else {
                    editmy.setVisibility(View.GONE);
                }
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);

            }
        });


    }

    private void inits() {

        for (int i = 0; i < 1; i++) {
            Income income = new Income("兼职", R.mipmap.ic_work);
            incomes.add(income);
            Income income1 = new Income("理财", R.mipmap.ic_money1);
            incomes.add(income1);
            Income income2 = new Income("红包", R.mipmap.ic_bill);
            incomes.add(income2);
            Income income3 = new Income("投资", R.mipmap.ic_tou);
            incomes.add(income3);
            Income income4 = new Income("奖金", R.mipmap.ic_pay);
            incomes.add(income4);
            Income income5 = new Income("其他", R.mipmap.ic_java);
            incomes.add(income5);
        }
    }


    //成功了
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        if (activity instanceof In_output) {
            textView = ((CallBackvalue) context).gettexview();
            imageView = ((CallBackvalue) context).getimgeview();
            editmy = ((CallBackvalue) context).getMyEdit();
        } else return;
    }


}
