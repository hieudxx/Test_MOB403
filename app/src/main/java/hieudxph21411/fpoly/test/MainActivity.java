package hieudxph21411.fpoly.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import hieudxph21411.fpoly.test.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msv = binding.edMaSV.getText().toString();
                String hoten = binding.edHoTen.getText().toString();
                String email = binding.edEmail.getText().toString();
                String add = binding.edAddress.getText().toString();
                String input = binding.edPoint.getText().toString().trim();
                String img = binding.edImg.getText().toString();

                if (msv.isEmpty() || hoten.isEmpty() || email.isEmpty() || add.isEmpty() || input.isEmpty() || img.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else if (!input.matches("\\d+")) {
                    Toast.makeText(MainActivity.this, "Điểm nhập số không nhập chữ", Toast.LENGTH_SHORT).show();
                } else {
                    int point = Integer.parseInt(input);
                    if (point < 0 || point > 10) {
                        Toast.makeText(MainActivity.this, "Điểm trong khoảng 0 - 10", Toast.LENGTH_SHORT).show();
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
                                if (response.isSuccessful()){
                                    Toast.makeText(MainActivity.this, response.message()+"", Toast.LENGTH_SHORT).show();
                                    clear();
                                }
                            }

                            @Override
                            public void onFailure(Call<SinhVien> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void clear(){
        binding.edMaSV.setText("");
        binding.edHoTen.setText("");
        binding.edEmail.setText("");
        binding.edAddress.setText("");
        binding.edPoint.setText("");
        binding.edImg.setText("");
    }

}