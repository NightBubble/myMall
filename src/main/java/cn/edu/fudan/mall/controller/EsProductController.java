package cn.edu.fudan.mall.controller;

import cn.edu.fudan.mall.common.api.CommonPage;
import cn.edu.fudan.mall.common.api.CommonResult;
import cn.edu.fudan.mall.mbg.model.PmsProduct;
import cn.edu.fudan.mall.nosql.elsaticsearch.pojo.EsProduct;
import cn.edu.fudan.mall.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Api(tags="EsProductController", description="商品搜索管理")
@RequestMapping(value="/esProduct")
@Controller
public class EsProductController {
    private Logger LOGGER = LoggerFactory.getLogger(EsProductController.class);
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value="导入所有商品")
    @RequestMapping(value="/importAllProducts",method = RequestMethod.POST)
    @ResponseBody
    CommonResult<Integer> importAllProducts(){
        int res = esProductService.importPmsProduct();
        if(res==0){
            return CommonResult.failed("未能导入商品");
        }
        return CommonResult.success(res,String.format("成功导入%d件商品",res));
    }

    @ApiOperation(value="根据id删除商品")
    @RequestMapping(value="/deleteById", method=RequestMethod.POST)
    @ResponseBody
    CommonResult<Object> deleteById(@RequestParam("id")Long id){
        esProductService.deleteById(id);
        return CommonResult.success("删除商品成功");
    }

    @ApiOperation(value="批量删除商品")
    @RequestMapping(value="/deleteByList", method = RequestMethod.POST)
    @ResponseBody
    CommonResult<Object> delete(@RequestParam("idList") ArrayList<Long> idList){
        esProductService.delete(idList);
        return CommonResult.success("批量删除商品成功");
    }

    @ApiOperation(value="添加商品")
    @RequestMapping(value="/importPmsProduct", method = RequestMethod.POST)
    @ResponseBody
    CommonResult<EsProduct> importPmsProduct(@RequestBody PmsProduct pmsProduct){
        EsProduct result = esProductService.create(pmsProduct);
        return CommonResult.success(result,"添加商品成功");
    }

    @ApiOperation(value="简单搜索")
    @RequestMapping(value="/sampleSearch", method=RequestMethod.POST)
    @ResponseBody
    CommonResult<CommonPage<EsProduct>> simpleSearch(@RequestParam("words") String words,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue="1")int pageSize,
                                                     @RequestParam(value = "pageNum", required = false, defaultValue="1")int pageNum){
        Page<EsProduct> page = esProductService.search(words, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page.toList()),"查询成功");
    }

}
