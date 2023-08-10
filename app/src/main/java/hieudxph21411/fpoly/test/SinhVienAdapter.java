package hieudxph21411.fpoly.test;

import static hieudxph21411.fpoly.test.ListActivity.binding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hieudxph21411.fpoly.test.databinding.ActivityListBinding;
import hieudxph21411.fpoly.test.databinding.DialogEditBinding;
import hieudxph21411.fpoly.test.databinding.SvItemRcvBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SinhVien> list;
    private AlertDialog.Builder builder;

    public SinhVienAdapter(Context context, ArrayList<SinhVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SvItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.sv_item_rcv, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(this.context).load(list.get(position).getImg()).into(holder.binding.imgAvt);
        holder.binding.tvMaSV.setText(list.get(position).getMaSV());
        holder.binding.tvHoTen.setText(list.get(position).getHoTen());
        holder.binding.tvEmail.setText(list.get(position).getEmail());
        holder.binding.tvAddress.setText(list.get(position).getAddress());
        holder.binding.tvPoint.setText(list.get(position).getPoint() + "");

        holder.binding.imgDelete.setOnClickListener(view -> {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa ?");
            builder.setPositiveButton("Không", null);
            builder.setNegativeButton("Có", (dialog, which) -> {
                APISinhVien.apiSV.deleteSv(list.get(position).get_id()).enqueue(new Callback<SinhVien>() {
                    @Override
                    public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                            ListActivity.getData();
                        }
                    }

                    @Override
                    public void onFailure(Call<SinhVien> call, Throwable t) {
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

            });
            builder.show();
        });

        holder.binding.imgEdit.setOnClickListener(view -> {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = (ViewGroup) inflater.inflate(R.layout.dialog_edit, null);
            DialogEditBinding editBinding = DialogEditBinding.inflate(inflater, (ViewGroup) view, false);
            builder = new AlertDialog.Builder(context);

            builder.setView(editBinding.getRoot());
            AlertDialog dialog = builder.create();
            dialog.show();

            Glide.with(this.context).load(list.get(position).getImg()).into(editBinding.imgAvt);
            editBinding.edMaSV.setText(list.get(position).getMaSV());
            editBinding.edHoTen.setText(list.get(position).getHoTen());
            editBinding.edEmail.setText(list.get(position).getEmail());
            editBinding.edAddress.setText(list.get(position).getAddress());
            editBinding.edPoint.setText(list.get(position).getPoint() + "");
            editBinding.edImg.setText(list.get(position).getImg() + "");

            editBinding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String msv = editBinding.edMaSV.getText().toString();
                    String hoten = editBinding.edHoTen.getText().toString();
                    String email = editBinding.edEmail.getText().toString();
                    String add = editBinding.edAddress.getText().toString();
                    String input = editBinding.edPoint.getText().toString().trim();
                    String img = editBinding.edImg.getText().toString();
                    if (msv.isEmpty() || hoten.isEmpty() || email.isEmpty() || add.isEmpty() || input.isEmpty() || img.isEmpty()) {
                        Toast.makeText(context, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else if (!input.matches("\\d+")) {
                        Toast.makeText(context, "Điểm nhập số không nhập chữ", Toast.LENGTH_SHORT).show();
                    } else {
                        int point = Integer.parseInt(input);
                        if (point < 0 || point > 10) {
                            Toast.makeText(context, "Điểm trong khoảng 0 - 10", Toast.LENGTH_SHORT).show();
                        } else {
                            SinhVien sv = new SinhVien();
                            sv.setMaSV(msv);
                            sv.setHoTen(hoten);
                            sv.setEmail(email);
                            sv.setAddress(add);
                            sv.setPoint(point);
                            sv.setImg(img);
                            APISinhVien.apiSV.editSv(list.get(position).get_id(), sv).enqueue(new Callback<SinhVien>() {
                                @Override
                                public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                        ListActivity.getData();
                                        dialog.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Call<SinhVien> call, Throwable t) {
                                    Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
            editBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editBinding.edMaSV.setText("");
                    editBinding.edHoTen.setText("");
                    editBinding.edEmail.setText("");
                    editBinding.edAddress.setText("");
                    editBinding.edPoint.setText("");
                    editBinding.edImg.setText("");
                }
            });


        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,DetailActivity.class).putExtra("_id", list.get(position).get_id()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SvItemRcvBinding binding;

        public ViewHolder(@NonNull SvItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
