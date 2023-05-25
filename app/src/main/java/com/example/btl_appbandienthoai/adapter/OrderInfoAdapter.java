package com.example.btl_appbandienthoai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl_appbandienthoai.Home;
import com.example.btl_appbandienthoai.R;
import com.example.btl_appbandienthoai.model.DetailOrder;
import com.example.btl_appbandienthoai.model.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.OrderInfoProductViewHolder> {
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private List<DetailOrder> listDetailOrder;
    private List<Order> listOrder;
    private Order orderInfo;
    private Home home;

    public void setData(Order orderInfo,Home home) {
        this.listDetailOrder = orderInfo.getListDetailOrder();
        this.orderInfo = orderInfo;
        this.listOrder = orderInfo.getListOder();
        this.home = home;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderInfoProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new  OrderInfoProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderInfoProductViewHolder holder, int position) {
        DetailOrder detailOrder = listDetailOrder.get(position);
        if(detailOrder == null){
            return;
        }else{
            Glide.with(holder.imgHitoryProduct.getContext()).load(detailOrder.getUrlImg()).into(holder.imgHitoryProduct);
            holder.tvHitoryProductName.setText(detailOrder.getProductName());
            holder.tvHitoryProductNum.setText(String.valueOf(detailOrder.getNumProduct()));
            holder.tvHitoryProductPrice.setText(formatPrice.format(detailOrder.getProductPrice()) + "VNĐ");
            holder.tvHitoryProductStatus.setText(detailOrder.getStatus());
            holder.tvHitoryProductOrderNo.setText(detailOrder.getOrderNo().toUpperCase());
        }
        for (Order order : listOrder) {
            if(order.getOrderNo().equals(detailOrder.getOrderNo())){
                holder.tvHitoryProductDate.setText(order.getDateOrder());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(listDetailOrder.size() != 0){
            return listDetailOrder.size();
        }else{
            return 0;
        }
    }

    public class OrderInfoProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHitoryProduct;
        TextView tvHitoryProductName,tvHitoryProductNum,tvHitoryProductPrice,tvHitoryProductDate
                ,tvHitoryProductStatus,tvHitoryProductOrderNo;

        public OrderInfoProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHitoryProduct = itemView.findViewById(R.id.img_hitory_product);
            tvHitoryProductName = itemView.findViewById(R.id.tv_hitory_product_name);
            tvHitoryProductNum = itemView.findViewById(R.id.tv_hitory_product_num);
            tvHitoryProductPrice = itemView.findViewById(R.id.tv_hitory_product_price);
            tvHitoryProductDate = itemView.findViewById(R.id.tv_hitory_product_date);
            tvHitoryProductStatus = itemView.findViewById(R.id.tv_hitory_product_status);
            tvHitoryProductOrderNo = itemView.findViewById(R.id.tv_hitory_product_orderNo);
        }
    }
}
