#CRAWLER

This project has been belong to [Higgsup Co., Ltd.](https://web.higgsup.com/?lang=en) , it serves for many purpose such as training, demo for clients, etc.

![Higgsup Co., Ltd.](https://web.higgsup.com/mediacenter/media/images/1881/logo/607bb23c-fe17-45f4-ade9-b134381d1c91-1519792738.png)

Crawler project is a sub-project serve for create data demo for Higgsup's Ecommerce Demo project

### Technologies Used

JDK 8

Maven 3.6

Selenium 3.7.0

References:

https://docs.oracle.com/en/java/javase/11/

https://maven.apache.org/guides/index.html

https://maven.apache.org/docs/3.6.0/release-notes.html

https://www.seleniumhq.org

### Installing 

Before running project, we need to config some parameters in `ApplicationProperties.java`

```java
public class ApplicationProperties {

  /*Dynamic Config*/

  public static final String LINK_PARENT = "https://www.lazada.vn/trang-diem/?spm=a2o4n.pdp.cate_4.1.63912a8e51DPgu";

  public static final String PROXY_SERVER = "183.90.191.93:1080";

  public static final int CATEGORY_ID = 23;

  public static final String OUTPUT_FILE_PATH = "C:\\Users\\thanhdt\\Desktop\\fullData.txt";

}
```

`LINK_PARENT: link list project of category`

`PROXY_SERVER: set proxy server address (xxx.xxx.xxx.xxx:port)`

`CATEGORY_ID: set category for product`

`OUTPUT_FILE_PATH: set output file path`

After set parameters, we will run project with `Run.java`

###Result 

The result of this project will have SQL statement format like this:

```sql
INSERT INTO product (id, name, short_desc, full_desc, category_id, brand_name, status, weight, available_item, unit_price, discount_percent, avg_rating, img_url, total_rating) VALUES(230, 'Son lì mịn môi L''Oreal Paris Color Riche Matte 3.7g', 'Son lì mịn môi L''Oreal Paris Color Riche Matte 3.7g', '<pre>Kết cấu son mềm mịn
Thành phần son chứa dưỡng ẩm cao
Mang đến đôi môi quyến rũ
Không chứa paraben
Dùng được cho mọi làn da</pre>', 23, 'L''Oreal Paris', 'null', 3.628397771990615, 3, 155000, 35, 4.2, 'https://vn-test-11.slatic.net/p/fc405e11c35b56c816b2a7e2e0574dc2.jpg;https://vn-test-11.slatic.net/p/9b9fbd6ef45d73338833d4e25442c97d.jpg;https://vn-test-11.slatic.net/p/48aea11f05e053fa399da305a2a709bf.jpg;https://vn-test-11.slatic.net/p/98d5d60435fa637088efb0926ea78b10.jpg;https://vn-test-11.slatic.net/p/4a42fac347016033e7aae26ed3ab5fcd.jpg;', 1853);

INSERT INTO review (id, product_id, reviewer, rating, `comment`) VALUES(2300, 230, 'Nhi', 0, '');

INSERT INTO review (id, product_id, reviewer, rating, `comment`) VALUES(2301, 230, 'Lazada Customer', 4, 'Mềm môi,xuất sắc❤❤');

INSERT INTO review (id, product_id, reviewer, rating, `comment`) VALUES(2302, 230, 'Kung N.', 2, 'Son k giống màu');

INSERT INTO review (id, product_id, reviewer, rating, `comment`) VALUES(2303, 230, 'Quách N. từ lazada.vn', 0, 'Đặt mua màu 263 hồng tím như ảnh mà nhận hàng mở son ra chán hẳn. Mã son chỉ là tem dán giấy có thể bóc ra dán lại (k phải là mã son khắc hc in lên vỏ như nhiều hãng khác). Tem dán là 263 mà ruột son màu nâu thẫm. Màu này chỉ hoá trang chứ trang điểm gì. Treo đầu dê bán thịt chó. Giảm giá thương hiệu lớn mà thế thì khách cũng chỉ mua 1 lần thôi.');

INSERT INTO review (id, product_id, reviewer, rating, `comment`) VALUES(2304, 230, 'Ninh H. từ lazada.vn', 0, 'shop giao hàng nhanh, đúng màu, đóng gói kỹ. Mình vừa thử lên tay thôi thì thấy chất son cũng tốt, màu lên đẹp giống hình, son không có mùi. Mua được deal 98k nên thấy hài lòng lắm.');
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Thanh Do Trung** - *Developer*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
