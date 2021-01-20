package cn.edu.fudan.mall.controller;

import cn.edu.fudan.mall.common.api.CommonPage;
import cn.edu.fudan.mall.common.api.CommonResult;
import cn.edu.fudan.mall.mbg.model.PmsBrand;
import cn.edu.fudan.mall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "PmsBradController", description = "商品品牌管理")
@Controller
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService pmsBrandService;

    private static final Logger logger = LoggerFactory.getLogger(PmsBrandController.class);

    @ApiOperation("获取所有品牌列表")
    @RequestMapping(value="/listAll", method= RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList(){
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    @ApiOperation("添加品牌")
    @RequestMapping(value="/create", method=RequestMethod.POST)
    @ResponseBody
    public CommonResult<PmsBrand> createPmsBrand(@RequestBody PmsBrand pmsBrand){
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrand);
        if(count==0){
            commonResult = CommonResult.failed("创建失败");
            logger.debug("createPmsBrand success:{}", pmsBrand);
        }else{
            commonResult = CommonResult.success("创建成功");
            logger.debug("createPmsBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    @ApiOperation("更新品牌")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @ResponseBody
    public CommonResult<PmsBrand> updateBrand(@PathVariable("id")long id, @RequestBody PmsBrand pmsBrand, BindingResult
            result){
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id, pmsBrand);
        if(count == 1){
            commonResult = CommonResult.success(pmsBrand);
            logger.debug("update pmsBrand success:{}", pmsBrand);
        }else{
            commonResult = CommonResult.failed("更新失败");
            logger.debug("update pmsBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    @ApiOperation("删除品牌")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id")long id){
        int count = pmsBrandService.deleteBrand(id);
        CommonResult commonResult;
        if(count == 1){
            logger.debug("delete pmsBrand success :id={}", id);
            commonResult =  CommonResult.success(null);
        }else{
            logger.debug("delete pmsBrand failed :id={}", id);
            commonResult =  CommonResult.failed("删除失败");
        }
        return commonResult;
    }

    @ApiOperation("分页查找品牌")
    @RequestMapping(value ="/list", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listPage(@RequestParam(value = "id", defaultValue = "1")Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize){
        List<PmsBrand> brandList = pmsBrandService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @ApiOperation("获取制定品牌id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> brand(@PathVariable("id")long id){
        CommonResult commonResult;
        PmsBrand pmsBrand = pmsBrandService.getBrand(id);
        if(pmsBrand == null){
            logger.debug("select brand failed :id={}",id);
            commonResult = CommonResult.failed("查找失败");
        }else{
            logger.debug("select brand success :id={}",id);
            commonResult = CommonResult.success(pmsBrand, "查找成功");
        }
        return commonResult;
    }


}
