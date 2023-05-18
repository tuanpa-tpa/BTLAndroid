package com.example.btl_appbandienthoai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.example.btl_appbandienthoai.Class.Order;
import com.example.btl_appbandienthoai.Class.Product;
import com.example.btl_appbandienthoai.Fragment.CartFragment;
import com.example.btl_appbandienthoai.Fragment.DetailProductFragment;
import com.example.btl_appbandienthoai.Fragment.HistoryFragment;
import com.example.btl_appbandienthoai.Fragment.OrderInfoFragment;
import com.example.btl_appbandienthoai.Fragment.ProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private List<Product> listCartProduct;
    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listCartProduct = new ArrayList<>();
        // Khởi tạo các item
        initItem();
        // Set data cho ahBotNavHome
        setDataBotNavHome();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Connect success");
    }

    private void setDataBotNavHome() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_item_list:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new ProductFragment());
                        fragmentTransaction.commit();
                        break;
                    case R.id.menu_item_info:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new CartFragment(listCartProduct));
                        fragmentTransaction.commit();
                        break;
                    case R.id.menu_item_search:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new HistoryFragment());
                        fragmentTransaction.commit();
                        break;
                }
                return true;
            }
        });
    }

    private void initItem() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        if(listCartProduct == null){
//            listCartProduct = new ArrayList<>();
//        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new ProductFragment());

        fragmentTransaction.commit();
    }

    // Mở Fragment DetailProduct
    public void toDetailProductFragment(Product product){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new DetailProductFragment(product,listCartProduct));
        fragmentTransaction.commit();
    }
    // Thêm sản phẩm đã chọn vào giỏ hàng
    public void addToListCartProdct(Product product){
        listCartProduct.add(product);
    }
    // Set lại số lượng của sản phẩm khi mua nhiều
    public void setCountForProduct(int possion, int countProduct){
        listCartProduct.get(possion).setNumProduct(countProduct);
    }

    // Lấy ra các sản phẩm đã thêm vào giỏ hàng
    public List<Product> getListCartProduct() {
        return listCartProduct;
    }

    // Mở Fragment OrderInfo
    public void toOrderInfoFragment(Order orderInfo){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new OrderInfoFragment(orderInfo));
        fragmentTransaction.addToBackStack(OrderInfoFragment.TAG);
        fragmentTransaction.commit();
    }

    private List<Product> createDataProduct(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DbProduct");

        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setId("product1");
        product1.setProductName("iPhone 14 Pro Max 256GB");
        product1.setUrlImg("https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-den-thumb-600x600.jpg");
        product1.setBrand("Apple");
        product1.setProductPrice(29790000);
        product1.setDescription("iPhone 14 Pro Max một siêu phẩm trong giới smartphone được nhà Táo tung ra thị trường vào tháng 09/2022. Máy trang bị con chip Apple A16 Bionic vô cùng mạnh mẽ, đi kèm theo đó là thiết kế hình màn hình mới, hứa hẹn mang lại những trải nghiệm đầy mới mẻ cho người dùng iPhone.");
        productList.add(product1);

        Product product2 = new Product();
        product2.setId("product2");
        product2.setProductName("Samsung Galaxy S22 Ultra 5G");
        product2.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/271698/Galaxy-S22-Ultra-Black-600x600.jpg");
        product2.setBrand("Samsung");
        product2.setProductPrice(27990000);
        product2.setDescription("Samsung Galaxy S22 Ultra 5G là một trong những flagship được mong đợi nhất của Samsung trong năm 2023. Máy được trang bị chip Exynos 2200 mạnh mẽ và camera độ phân giải cao, hứa hẹn mang đến trải nghiệm tuyệt vời cho người dùng.");
        productList.add(product2);

        Product product3 = new Product();
        product3.setId("product3");
        product3.setProductName("Xiaomi Mi 13");
        product3.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/267984/xiaomi-13-thumb-den-600x600.jpg");
        product3.setBrand("Xiaomi");
        product3.setProductPrice(18990000);
        product3.setDescription("Xiaomi Mi 13 là chiếc smartphone cao cấp mới nhất của Xiaomi, được trang bị chip Snapdragon 8 Gen 1 và camera 108 MP đầu tiên trên thế giới. Với thiết kế đẹp và tính năng ấn tượng, đây là một lựa chọn tuyệt vời cho người dùng Android.");
        productList.add(product3);

        Product product4 = new Product();
        product4.setId("product4");
        product4.setProductName("Oppo Find X5 Pro");
        product4.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/250622/oppo-find-x5-pro-den-thumb-600x600.jpg");
        product4.setBrand("Oppo");
        product4.setProductPrice(21990000);
        product4.setDescription("Oppo Find X5 Pro là một chiếc smartphone cao cấp với màn hình cong OLED 6.7 inch, độ phân giải cao và độ sáng lên đến 1200 nits. Máy trang bị chip Snapdragon 8 Gen 1 và camera 50 MP, hứa hẹn mang đến trải nghiệm đỉnh cao cho người dùng.");
        productList.add(product4);

        Product product5 = new Product();
        product5.setId("product5");
        product5.setProductName("Realme GT Neo 2");
        product5.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/250098/realme-gt-neo2-1-600x600.jpg");
        product5.setBrand("Realme");
        product5.setProductPrice(8990000);
        product5.setDescription("Realme GT Neo 2 là một chiếc smartphone tầm trung với thiết kế đẹp mắt và hiệu năng mạnh mẽ. Máy được trang bị chip Snapdragon 870, camera 64 MP và pin 5000 mAh. Với giá thành hợp lý, đây là một trong những sự lựa chọn tốt nhất trong phân khúc tầm trung.");
        productList.add(product5);

        Product product6 = new Product();
        product6.setId("product6");
        product6.setProductName("Samsung Galaxy Z Fold 3");
        product6.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/248284/samsung-galaxy-z-fold-3-green-1-600x600.jpg");
        product6.setBrand("Samsung");
        product6.setProductPrice(50000000);
        product6.setDescription("Samsung Galaxy Z Fold 3 là một chiếc điện thoại độc đáo với màn hình gập hai lớp, cho phép người dùng dễ dàng sử dụng như một chiếc tablet hoặc một chiếc điện thoại thông thường. Máy trang bị chip Snapdragon 888 và camera 12 MP, hứa hẹn mang đến trải nghiệm đặc biệt cho người dùng.");
        productList.add(product6);

        Product product7 = new Product();
        product7.setId("product7");
        product7.setProductName("Xiaomi 11T Pro 5G");
        product7.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/248218/Xiaomi-11T-Pro-Blue-1-2-600x600.jpg");
        product7.setBrand("Xiaomi");
        product7.setProductPrice(13990000);
        product7.setDescription("Xiaomi 11T Pro 5G là một chiếc smartphone tầm trung với thiết kế đẹp mắt và hiệu năng mạnh mẽ. Máy được trang bị chip Snapdragon 870 và camera 108 MP, hứa hẹn mang đến trải nghiệm đỉnh cao cho người dùng.");
        productList.add(product7);

        Product product8 = new Product();
        product8.setId("product8");
        product8.setProductName("Vivo X70 Pro+");
        product8.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/236978/vivo-x70-pro-plus-1-600x600.jpg");
        product8.setBrand("Vivo");
        product8.setProductPrice(20990000);
        product8.setDescription("Vivo X70 Pro+ là một chiếc smartphone cao cấp với thiết kế đẹp mắt và hiệu năng mạnh mẽ. Máy được trang bị chip Snapdragon 888 và camera 50 MP, hứa hẹn mang đến trải nghiệm đỉnh cao cho người dùng.");
        productList.add(product8);

        Product product9 = new Product();
        product9.setId("product9");
        product9.setProductName("Asus Zenfone 8 Flip");
        product9.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/236182/asus-zenfone-8-flip-600x600.jpg");
        product9.setBrand("Asus");
        product9.setProductPrice(19990000);
        product9.setDescription("Asus Zenfone 8 Flip là một chiếc smartphone đặc biệt với camera xoay 180 độ, cho phép người dùng chụp ảnh selfie và video với góc rộng hoặc zoom xa. Máy được trang bị chip Snapdragon 888 và pin 5000 mAh, hứa hẹn sẽ đem đến nhiều trải nghiệm cho người dùng.");
        productList.add(product9);

        Product product10 = new Product();
        product10.setId("product10");
        product10.setProductName("Realme GT Neo 2");
        product10.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/250098/realme-gt-neo2-1-600x600.jpg");
        product10.setBrand("Realme");
        product10.setProductPrice(7990000);
        product10.setDescription("Realme GT Neo 2 là một chiếc smartphone tầm trung với thiết kế đẹp mắt và hiệu năng mạnh mẽ. Máy được trang bị chip Snapdragon 870 và camera 64 MP, hứa hẹn mang đến trải nghiệm đỉnh cao cho người dùng.");
        productList.add(product10);

        Product product11 = new Product();
        product11.setId("product11");
        product11.setProductName("Samsung Galaxy A52s 5G");
        product11.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/251879/samsung-galaxy-a52s-5g-white-600x600.jpg");
        product11.setBrand("Samsung");
        product11.setProductPrice(9990000);
        product11.setDescription("Samsung Galaxy A52s 5G là một chiếc smartphone tầm trung với nhiều tính năng nổi bật như camera 64 MP, chip Snapdragon 778G và hỗ trợ 5G. Thiết kế đẹp mắt và pin dung lượng lớn 4500 mAh giúp máy hoạt động liên tục trong nhiều giờ đồng hồ.");
        productList.add(product11);

        Product product12 = new Product();
        product12.setId("product12");
        product12.setProductName("OPPO Reno7 Pro 5G");
        product12.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/251964/OPPO-Reno7-Pro-5G-thumb-den-600x600.jpg");
        product12.setBrand("OPPO");
        product12.setProductPrice(15990000);
        product12.setDescription("OPPO Reno7 Pro 5G là chiếc smartphone cao cấp mới nhất của OPPO với nhiều tính năng nổi bật như camera sau 50 MP, camera trước 32 MP, hỗ trợ 5G và bộ nhớ trong lên đến 256 GB. Thiết kế sang trọng với màn hình cong 6.55 inch giúp máy trở nên đẳng cấp và sang trọng hơn.");
        productList.add(product12);

        Product product13 = new Product();
        product13.setId("product13");
        product13.setProductName("Vivo Y72 5G");
        product13.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/236431/vivo-y72-5g-blue-600x600.jpg");
        product13.setBrand("Vivo");
        product13.setProductPrice(7490000);
        product13.setDescription("Vivo Y72 5G là một chiếc smartphone tầm trung với thiết kế đẹp mắt và hiệu năng ổn định. Máy được trang bị chip MediaTek Dimensity 700 5G và camera 64 MP, hứa hẹn mang đến trải nghiệm đỉnh cao cho người dùng.");
        productList.add(product13);

        Product product14 = new Product();
        product14.setId("product14");
        product14.setProductName("Realme GT Neo2");
        product14.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/250098/realme-gt-neo2-1-600x600.jpg");
        product14.setBrand("Realme");
        product14.setProductPrice(8990000);
        product14.setDescription("Realme GT Neo2 là chiếc smartphone tầm trung mới nhất của Realme với nhiều tính năng nổi bật như màn hình 120Hz, camera 64 MP, chip Snapdragon 870 5G và pin 5000 mAh. Thiết kế đẹp mắt và giá cả hợp lý giúp máy trở nên phổ biến với người dùng.");
        productList.add(product14);

        Product product15 = new Product();
        product15.setId("product15");
        product15.setProductName("Xiaomi Redmi Note 11 Pro");
        product15.setUrlImg("https://cdn.tgdd.vn/Products/Images/42/246640/xiaomi-redmi-note-11-pro-5g-xam-thumb-600x600.jpg");
        product15.setBrand("Xiaomi");
        product15.setProductPrice(8490000);
        product15.setDescription("Xiaomi Redmi Note 11 Pro là một chiếc smartphone tầm trung với nhiều tính năng nổi bật như camera sau 64 MP, camera trước 16 MP và chip MediaTek Helio G95. Thiết kế đẹp mắt và màn hình AMOLED giúp máy trở nên hấp dẫn hơn với người dùng.");
        productList.add(product15);

        for (Product product : productList) {
            myRef.child(product.getId()).setValue(product);
        }
        List<Product> mListProduct = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Snapshot",snapshot.toString());
                for (DataSnapshot data : snapshot.getChildren()){
                    Product product = data.getValue(Product.class);
                    product.setId(data.getKey());
                    mListProduct.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MYTAG","onCancelled"+ error.toString());
            }
        });
        return mListProduct;
    }

}