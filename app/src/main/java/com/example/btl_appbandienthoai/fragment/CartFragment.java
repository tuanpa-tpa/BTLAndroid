package com.example.btl_appbandienthoai.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbandienthoai.adapter.ProductCartAdapter;
import com.example.btl_appbandienthoai.model.DetailOrder;
import com.example.btl_appbandienthoai.model.Product;
import com.example.btl_appbandienthoai.Home;
import com.example.btl_appbandienthoai.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartFragment extends Fragment {


    private int totalPrice;
    private View mView;
    private Home home;
    private DecimalFormat format;

//    2 layout để hiện thị 2 trạng thái cảu giỏ hàng
    private RelativeLayout rlCartEmpty,rlCart;

//    các item trong cart k trống
    private List<Product> listCartProduct;
    private RecyclerView rcvCartProduct;
    private TextView tvCartTotalPrice;
    private EditText edtCartCustName,edtCartCustAddress,edtCartCustPhone;
    private Button btnCartOrder;

    private ProductCartAdapter productCartAdapter;
    public CartFragment(List<Product> listCartProduct) {
        this.listCartProduct = new ArrayList<>();
        this.listCartProduct = listCartProduct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart,container,false);
        // Khởi tạo các item
        initItem();

        // Set hiển thị các view
        setVisibilityView();
        return mView;
    }

    // Khởi tạo các item
    private void initItem(){
        productCartAdapter = new ProductCartAdapter();
        rlCartEmpty = mView.findViewById(R.id.rl_cart_empty);
        rlCart = mView.findViewById(R.id.rl_cart);
        rcvCartProduct = mView.findViewById(R.id.rcv_cart_product);
        tvCartTotalPrice = mView.findViewById(R.id.tv_cart_total_price);
        edtCartCustName = mView.findViewById(R.id.edt_cart_cust_name);
        edtCartCustAddress = mView.findViewById(R.id.edt_cart_cust_address);
        edtCartCustPhone = mView.findViewById(R.id.edt_cart_cust_phone);
        btnCartOrder = mView.findViewById(R.id.btn_cart_order);
        btnCartOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                // Thêm dữ liệu các thông tin đã order
                addDataOrder();
            }
        });

        home = (Home) getActivity();
        format = new DecimalFormat("###,###,###");
    }
    // Set trạng thái hiển thị các view
    // Set trạng thái hiển thị các view
    private void setVisibilityView(){
        if (listCartProduct.size() == 0){

            // Hiển thị giỏ hàng rỗng
            setVisibilityEmptyCart();
        }else {

            // Hiển thị giỏ hàng
            setVisibilityCart();
            setDataProductCartAdapter();
        }
    }
    // set data ProductCartAdapter
    private void setDataProductCartAdapter(){
        productCartAdapter.setData(listCartProduct,home,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home,RecyclerView.VERTICAL,false);
        rcvCartProduct.setLayoutManager(linearLayoutManager);
        rcvCartProduct.setAdapter(productCartAdapter);
    }
    // Thêm dữ liệu các thông tin đã order
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addDataOrder() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DbOrder");

        // Kiểm tra và lấy giá trị từ các trường nhập liệu
        String custName = edtCartCustName.getText().toString();
        String custAddress = edtCartCustAddress.getText().toString();
        String custPhone = edtCartCustPhone.getText().toString();

        if (custName.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập tên khách hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (custAddress.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập địa chỉ khách hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        String phoneRegex = "^0\\d{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(custPhone);

        if (custPhone.isEmpty() || !phoneMatcher.matches()) {
            Toast.makeText(getContext(), "Số điện thoại nhập không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        // Tạo map chứa thông tin đơn hàng
        Map<String, Object> map = new HashMap<>();

        // Lấy ngày order = now
        Date date = new Date(System.currentTimeMillis());
        map.put("dateOrder", date.toString());
        map.put("custName", custName);
        map.put("custAddress", custAddress);
        map.put("custPhone", custPhone);

        int num = 0;
        for (Product product : listCartProduct) {
            num += product.getNumProduct();
        }
        map.put("numProduct", num);
        map.put("totalPrice", totalPrice);
        map.put("email",email);
        map.put("status", "Đang chờ xác nhận");

        // Add thông tin order
//        String odrKey = myRef.push().getKey();
        double randomDouble = Math.random();
        randomDouble = randomDouble * 100000 + 1;
        int randomInt = (int) randomDouble;
        String odrKey = randomInt+"";
//        Log.i("oderKey", odrKey);
        myRef.child(odrKey).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                List<DetailOrder> listDetailOrder = makeDetailOrder(odrKey);

                // Add thông tin detail order
                for (DetailOrder detailOrder : listDetailOrder) {
                    myRef.child(odrKey).child("detail").child(detailOrder.getProductName())
                            .setValue(detailOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Đã đăng ký đơn hàng", Toast.LENGTH_SHORT).show();
                                    listCartProduct.clear();
                                    setVisibilityEmptyCart();
                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Đăng ký đơn hàng thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Hiển thị giỏ hàng
    private void setVisibilityCart(){
        rlCartEmpty.setVisibility(View.GONE);
        rlCart.setVisibility(View.VISIBLE);
        String total = format.format(getTotalPrice());
        tvCartTotalPrice.setText( total +" VNĐ" );
    }

    // Hiển thị giỏ hàng rỗng
    public void setVisibilityEmptyCart(){
        rlCartEmpty.setVisibility(View.VISIBLE);
        rlCart.setVisibility(View.GONE);
    }


    // lấy giá trị tổng tiền tất cả sản phẩm trong giỏ hàng
    private int getTotalPrice(){
        for (Product product : listCartProduct){
            int priceProduct = product.getProductPrice() ;
            totalPrice = totalPrice +  priceProduct * product.getNumProduct();
        }
        return totalPrice;
    }

    //    lấy ra các sản phầm vừa thêm vào giỏ hàng để thêm vào danh sách hàng sau khi đã bấm đặt hàng
    private List<DetailOrder> makeDetailOrder(String odrNo) {
        List<DetailOrder> listDetailOrder = new ArrayList<>();

        for (Product product : home.getListCartProduct()) {
            DetailOrder detailOrder = new DetailOrder();

            if (odrNo == null || odrNo.isEmpty()) {
                Toast.makeText(getActivity(), "Số đơn hàng không hợp lệ", Toast.LENGTH_SHORT).show();
                return listDetailOrder;
            }
            detailOrder.setOrderNo(odrNo);

            if (product.getProductName() == null || product.getProductName().isEmpty()) {
                Toast.makeText(getActivity(), "Tên sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                return listDetailOrder;
            }
            detailOrder.setProductName(product.getProductName());

            if (product.getProductPrice() < 0) {
                Toast.makeText(getActivity(), "Giá sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                return listDetailOrder;
            }
            detailOrder.setProductPrice(product.getProductPrice());

            if (product.getUrlImg() == null || product.getUrlImg().isEmpty()) {
                Toast.makeText(getActivity(), "URL hình ảnh không hợp lệ", Toast.LENGTH_SHORT).show();
                return listDetailOrder;
            }
            detailOrder.setUrlImg(product.getUrlImg());

            if (product.getNumProduct() < 0) {
                Toast.makeText(getActivity(), "Số lượng sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                return listDetailOrder;
            }
            detailOrder.setNumProduct(product.getNumProduct());

            detailOrder.setStatus("Đang chờ xác nhận");
            listDetailOrder.add(detailOrder);
        }

        return listDetailOrder;
    }

    // Set giá trị hiển thị tổng tiền khi tăng giảm số lượng
    // mode = 0 : giảm
    // mode = 1 : tăng
    public void setTotalPrice(int mode,int count, int priceProduct ){
        if( mode == 0){
            totalPrice = totalPrice - priceProduct * count;
        }else if (mode == 1){
            totalPrice = totalPrice + priceProduct * count;
        }

        tvCartTotalPrice.setText( format.format(totalPrice) + " VNĐ");
    }

    // Set sô lượng sản phẩm sau nhấn nút tăng giảm
    public void setCountForProduct(int possion,int countProduct){
        listCartProduct.get(possion).setNumProduct(countProduct);
    }
}
