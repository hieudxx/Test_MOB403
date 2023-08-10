package hieudxph21411.fpoly.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import hieudxph21411.fpoly.test.databinding.ActivityListBinding;
import hieudxph21411.fpoly.test.databinding.DialogAddBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    // implements SinhVienAdapter.ItemEditClickListener, SinhVienAdapter.ItemDeleteClickListener
    public static ActivityListBinding binding;
    private static ArrayList<SinhVien> list;
    private static SinhVienAdapter adapter;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = view.getContext();
        getData();
        binding.fltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ListActivity.this, MainActivity.class));
                add();
            }
        });

    }


    private void add() {
        DialogAddBinding dialogBinding = DialogAddBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setView(dialogBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.show();

        dialogBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msv = dialogBinding.edMaSV.getText().toString();
                String hoten = dialogBinding.edHoTen.getText().toString();
                String email = dialogBinding.edEmail.getText().toString();
                String add = dialogBinding.edAddress.getText().toString();
                String input = dialogBinding.edPoint.getText().toString().trim();
                String img = dialogBinding.edImg.getText().toString();

                if (msv.isEmpty() || hoten.isEmpty() || email.isEmpty() || add.isEmpty() || input.isEmpty() || img.isEmpty()) {
                    Toast.makeText(ListActivity.this, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else if (!input.matches("\\d+")) {
                    Toast.makeText(ListActivity.this, "Điểm nhập số không nhập chữ", Toast.LENGTH_SHORT).show();
                } else {
                    int point = Integer.parseInt(input);
                    if (point < 0 || point > 10) {
                        Toast.makeText(ListActivity.this, "Điểm trong khoảng 0 - 10", Toast.LENGTH_SHORT).show();
                    } else {
                        SinhVien sv = new SinhVien();
                        sv.setMaSV(msv);
                        sv.setHoTen(hoten);
                        sv.setEmail(email);
                        sv.setAddress(add);
                        sv.setPoint(point);
                        sv.setImg(img);
                        APISinhVien.apiSV.addSv(sv).enqueue(new Callback<SinhVien>() {
                            @Override
                            public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ListActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    getData();
                                }
                            }

                            @Override
                            public void onFailure(Call<SinhVien> call, Throwable t) {
                                Toast.makeText(ListActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
        });

        dialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBinding.edMaSV.setText("");
                dialogBinding.edHoTen.setText("");
                dialogBinding.edEmail.setText("");
                dialogBinding.edAddress.setText("");
                dialogBinding.edPoint.setText("");
                dialogBinding.edImg.setText("");
            }
        });
    }

    public static void getData() {
        APISinhVien.apiSV.getAllSv().enqueue(new Callback<ArrayList<SinhVien>>() {
            @Override
            public void onResponse(Call<ArrayList<SinhVien>> call, Response<ArrayList<SinhVien>> response) {
                list = new ArrayList<>(response.body());
                DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                binding.rcv.addItemDecoration(itemDecoration);
                adapter = new SinhVienAdapter(context, list);
                binding.rcv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<SinhVien>> call, Throwable t) {
                Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


}