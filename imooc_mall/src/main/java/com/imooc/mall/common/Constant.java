package com.imooc.mall.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class Constant {
    public static final String SALT="()!fwhqjueolm./}]";
    public static final String IMOOC_MALL_USER="imooc_mall_user";


//    public static String FILE_UPLOAD_DIR ="C:\\photoPro\\";
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc",
                "price asc");
    }

    public interface SaleStatus{
        int NOT_SALE=0;
        int SALE=1;
    }

    public interface CartProductStatus{
        int UN_CHECKED=0;
        int CHECKED=1;
    }

}
