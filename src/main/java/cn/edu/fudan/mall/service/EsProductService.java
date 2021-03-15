package cn.edu.fudan.mall.service;

import cn.edu.fudan.mall.mbg.model.PmsProduct;
import cn.edu.fudan.mall.nosql.elsaticsearch.pojo.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 商品搜索管理
 */
public interface EsProductService {
    /**
     * 从数据库中导入所有商品到es
     * @param
     * @return
     */
    int importPmsProduct();

    /**
     * 根据id删除商品
     */
    void deleteById(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 新建商品
     * @param pmsProduct
     * @return
     */
    EsProduct create(PmsProduct pmsProduct);

    /**
     * 根据关键字搜索商品
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

}
