package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 后台商品管理
 */

@RestController
public class ProductAdminController {


    @Autowired
    ProductService productService;

    @ApiOperation("增加商品")
    @PostMapping("/admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }


    /**
     * 前端界面在操作时同时调用
     * @param httpServletRequest
     * @param file
     * @return
     */
    @ApiOperation("上传图片接口")
    @PostMapping("/admin/upload/add")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest,
                                  @RequestParam("file") MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        String newFileName=uuid.toString()+suffixName;
        //创建文件
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        System.out.println(fileDirectory);
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDirectory.exists()){
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.DIR_CREATE_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL()+""))+
                    "/images/"+newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.PHOTO_UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri){
        URI effectiveURI = null;
        try {
            effectiveURI = new URI(uri.getScheme(),uri.getUserInfo(),uri.getHost(),uri.getPort(),
                    null,null,null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
      return effectiveURI;
    }

    @ApiOperation("后台更新商品")
    @PostMapping("/admin/product/update")
    public ApiRestResponse update(@Valid @RequestBody UpdateProductReq updateProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq,product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台删除商品")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam Integer id){
        productService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("批量上下架功能")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids,
                                                 @RequestParam Integer sellStatus){
        productService.batchUpdateSellStatus(ids,sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品列表接口")
    @GetMapping("/admin/product/list")
    public ApiRestResponse list(@RequestParam Integer pageNumber,
                                                 @RequestParam Integer pageSize){
        PageInfo pageInfo = productService.listForAdmin(pageNumber, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

}
