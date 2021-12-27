package com.clothingShop.customer.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class CrawlerData {
    CrawlerImg crawlerImg = new CrawlerImg();

    public static DB db = new DB();

    private void processPage(ArrayList<String> url,String localhost){

        for (int i =0;i<url.size();i++){
            Document doc = null;
            try {
                doc = Jsoup.connect(url.get(i)).get();
                Elements elements = doc.getElementsByClass("product-info");

                for (Element e : elements) {
                    Web item = new Web();

                    String urlChild = localhost+e.child(0).child(0).attr("href");

                    item.setName(e.child(0).child(0).text());

                    String s = e.child(1).child(0).child(0).text();
                    item.setPrice(Float.parseFloat(s.substring(0,s.length()-2).replace(",","")));

                    Document doc2 = Jsoup.connect(urlChild).get();

                    Elements elements3 = doc2.getElementsByClass("breadcrumb");
                    for (Element s3 : elements3){
                        String category = s3.child(2).text();
                        if (category.equals("ĐỒ NỮ")){
                            item.setCategoryID(2);
                        }else if(category.equals("ĐỒ NAM")){
                            item.setCategoryID(1);
                        }else if (category.equals("Đồ Đôi")){
                            item.setCategoryID(3);
                        }else if (category.equals("Áo Khoác")){
                            item.setCategoryID(4);
                        }
                    }

                    String[] size = {"M","S","L","XL","XXL"};
                    Random random = new Random();
                    int local = random.nextInt(4);
                    item.setSize(size[local]);

                    int brandId = random.nextInt(2) +1;

                    item.setBrandID(brandId);

                    Element elements2 = doc2.getElementById("tab-description");
                    item.setDescription(elements2.html());

                    Element elementImg = doc2.getElementById("img-product");
                    String urlIMG = elementImg.attr("src");

                    String nameIMG = urlIMG.substring(67,urlIMG.length());
                    crawlerImg.savaImg(urlIMG,nameIMG);
                    Long id =insertProduct(item);
                    insertIMG(nameIMG,id);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public static void main(String[] args) {
        CrawlerData crawlerData = new CrawlerData();
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://totoshop.vn/do-nu-pc72896.html");
        urls.add("https://totoshop.vn/do-nu-pc72896.html?page=2");
        urls.add("https://totoshop.vn/do-nu-pc72896.html?page=3");
        urls.add("https://totoshop.vn/do-nu-pc72896.html?page=4");
        urls.add("https://totoshop.vn/do-nam-pc72882.html");
        urls.add("https://totoshop.vn/do-nam-pc72882.html?page=2");
        urls.add("https://totoshop.vn/do-nam-pc72882.html?page=3");
        urls.add("https://totoshop.vn/do-nam-pc72882.html?page=4");
        urls.add("https://totoshop.vn/do-doi-pc72920.html");
        urls.add("https://totoshop.vn/do-doi-pc72920.html?page=2");
        urls.add("https://totoshop.vn/do-doi-pc72920.html?page=3");
        urls.add("https://totoshop.vn/ao-khoac-pc72908.html");
        urls.add("https://totoshop.vn/ao-khoac-pc72908.html?page=2");
        urls.add("https://totoshop.vn/ao-khoac-pc72908.html?page=3");
        urls.add("https://totoshop.vn/ao-khoac-pc72908.html?page=4");
        crawlerData.processPage(urls,"https://totoshop.vn");
    }

    public void insertIMG(String name, Long productId){
        try {
            Long id = null;
            PreparedStatement stmt = null;
            ResultSet resultSet = null;
            String sql = "INSERT INTO Product_img(name, productId) " +
                    "values (?,?);";
            stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,name);
            stmt.setLong(2, productId);

            stmt.execute();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public Long insertProduct(Web web){
        try {
            Long id = null;
            PreparedStatement stmt = null;
            ResultSet resultSet = null;
            String sql = "INSERT INTO Product(description, name, price, size, stock, brandId, categoryId, views) " +
                    "values (?,?,?,?,?,?,?,?);";
            stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, web.getDescription());
            stmt.setString(2, web.getName());
            stmt.setFloat(3, web.getPrice());
            stmt.setString(4, web.getSize());
            stmt.setInt(5, 30);
            stmt.setInt(6, web.getBrandID());
            stmt.setInt(7, web.getCategoryID());
            stmt.setInt(8, 0);
            stmt.execute();
            resultSet = stmt.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }
}
