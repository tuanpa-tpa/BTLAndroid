package com.example.btl_appbandienthoai.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbandienthoai.model.DetailOrder;
import com.example.btl_appbandienthoai.model.Order;
import com.example.btl_appbandienthoai.Home;
import com.example.btl_appbandienthoai.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryProductAdapter extends RecyclerView.Adapter<HistoryProductAdapter.HistoryProductViewHolder> {
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private List<DetailOrder> listDetailOrder;
    private List<Order> listOrder;
    private Order orderInfo;
    private Home home;

    public void setData(List<DetailOrder> listDetailOrder, List<Order> listOrder, Home home) {
        this.listDetailOrder = new ArrayList<>();
        this.listDetailOrder = listDetailOrder;
        this.listOrder = listOrder;
        this.home = home;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false);
        return new HistoryProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryProductViewHolder holder, int position) {
//        DetailOrder detailOrder = listDetailOrder.get(position);
        Order order = listOrder.get(position);
        if (order == null) {
            return;
        } else {

            holder.tvHitoryProductDate.setText(order.getDateOrder());
            holder.tvHitoryName.setText("Người nhận: " + order.getCustName());
            holder.tvHitoryProductStatus.setText(order.getStatus());
            holder.tvHitoryProductOrderNo.setText(order.getOrderNo().toUpperCase());
        }

        holder.tvHitoryProductOrderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderInfo = order;
                List<DetailOrder> list = new ArrayList<>();
                for (DetailOrder itemDetailOrder : listDetailOrder) {
                    if (order.getOrderNo().equalsIgnoreCase(itemDetailOrder.getOrderNo())) {
                        list.add(itemDetailOrder);
                    }
                }
                orderInfo.setListDetailOrder(list);
                orderInfo.setListOder(listOrder);
                home.toOrderInfoFragment(orderInfo);
            }
        });
        holder.tvHitoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderInfo = order;
                List<DetailOrder> list = new ArrayList<>();
                for (DetailOrder itemDetailOrder : listDetailOrder) {
                    if (order.getOrderNo().equalsIgnoreCase(itemDetailOrder.getOrderNo())) {
                        list.add(itemDetailOrder);
                    }
                }
                orderInfo.setListDetailOrder(list);
                orderInfo.setListOder(listOrder);
                home.toOrderInfoFragment(orderInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listOrder.size() != 0) {
            return listOrder.size();
        } else {
            return 0;
        }
    }

    public class HistoryProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvHitoryName, tvHitoryProductDate, tvHitoryProductStatus, tvHitoryProductOrderNo;

        public HistoryProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHitoryName = itemView.findViewById(R.id.tv_search_hitory_product_name);
            tvHitoryProductDate = itemView.findViewById(R.id.tv_search_hitory_product_date);
            tvHitoryProductStatus = itemView.findViewById(R.id.tv_search_hitory_product_status);
            tvHitoryProductOrderNo = itemView.findViewById(R.id.tv_search_hitory_product_orderNo);
        }
    }
}
