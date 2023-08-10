package hieudxph21411.fpoly.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import hieudxph21411.fpoly.test.databinding.ActivityDetailBinding;
import hieudxph21411.fpoly.test.databinding.ActivityListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private SinhVien sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sv = new SinhVien();
        setSupportActionBar(binding.tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        APISinhVien.apiSV.getOneSv(getIntent().getStringExtra("_id")).enqueue(new Callback<SinhVien>() {
            @Override
            public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
                if (response.isSuccessful()) {
                    sv = response.body();
                    Glide.with(DetailActivity.this).load(sv.getImg()).into(binding.imgAvt);
                    binding.tvHoTen.setText(sv.getHoTen());
                    binding.tvMaSV.setText(sv.getMaSV()+"");
                    binding.tvEmail.setText(sv.getEmail());
                    binding.tvAddress.setText(sv.getAddress());
                    binding.tvPoint.setText(sv.getPoint()+"");
                }
            }

            @Override
            public void onFailure(Call<SinhVien> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // kết thúc Activity hiện tại
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}