package com.clothingShop.customer.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class CrawlerImg {

    private ArrayList<String> listImg(ArrayList<String> URL) throws IOException {
        ArrayList<String> list_img = new ArrayList<>();
        for (int i=1;i<URL.size();i++){
            Document document = Jsoup.connect(URL.get(i)).get();
            Elements elements = document.getElementsByClass("thumbblock thumb170x100");
            for (Element e : elements){
                String url = e.childNode(1).attr("src");
                if (url.equals("")) {
                    continue;
                }
                list_img.add(url);
            }
        }
        return list_img;
    }


    public void savaImg(String src,String name){
        try {
            URL url = new URL(src);
            InputStream in = url.openStream();
            OutputStream out = new FileOutputStream("/Users/admin/Desktop/codetheu/clothingShop/target/clothingShop-1.0-SNAPSHOT/img" + "/" +name);
            for(int b; (b = in.read()) != -1;){
                out.write(b);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Can not Dowload File !");
        }
    }

    public void saveFile(ArrayList<String> url) {
        try {
            ArrayList<String> list_img = listImg(url);
            for (int i = 0; i< list_img.size();i++){
                String name = i + ".jpg";
                savaImg(list_img.get(i),name);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error", "Error to save file !", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "Dowload sucessfull chap " + url);
    }
}
